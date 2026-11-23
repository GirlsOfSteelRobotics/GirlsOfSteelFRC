package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import org.wpilib.hardware.bus.CANPort;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkLowLevel.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.geometry.Rotation2d;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.interpolation.InterpolatingDoubleTreeMap;
// import org.wpilib.math.numbers.N1;
// import org.wpilib.math.system.LinearSystem;
// import org.wpilib.math.system.DCMotor;
// import org.wpilib.math.system.Models;
import org.wpilib.math.util.Units;
// import org.wpilib.framework.RobotBase;
import com.gos.lib.tunables.CommandTab;
// import org.wpilib.simulation.FlywheelSim;
import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
// import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
// import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
// import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
// import org.snobotv2.sim_wrappers.ISimWrapper;

import java.util.function.DoubleSupplier;

public class ShooterSubsystem extends SubsystemBase {
    public static final Rotation2d SHOT_ANGLE = Rotation2d.fromDegrees(60);
    private static final double DEADBAND = 100;
    private static final double MIN_DISTANCE = 2.53;

    private static final GosDoubleProperty HACK_ADDITIONAL_RPM = new GosDoubleProperty(false, "ShooterHackAdditionalRPM", -20);

    private final SparkFlex m_leader;
    private final SparkFlex m_follower;
    private final RelativeEncoder m_motorEncoder;
    private final RelativeEncoder m_followerEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final GosDoubleProperty m_shooterSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "shooterSpeed", 1);
    private final GosDoubleProperty m_tuneRpm = new GosDoubleProperty(false, "tuneRPM", 4200);
    private final SparkMaxAlerts m_shooterAlert;
    private final GosDoubleProperty m_speedBoost = new GosDoubleProperty(false, "speed booster", 1.1);

    // TODO(2027) restore simulation once SnobotSim supports 2027
    // private ISimWrapper m_shooterSimulator;
    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();
    private double m_goal;
    private final Debouncer m_debouncer;

    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;

    public static final Translation2d SHOOTER_OFFSET = new Translation2d(Units.inchesToMeters(7.72), Units.inchesToMeters(8.5));


    public ShooterSubsystem() {
        m_leader = new SparkFlex(CANPort.CAN_S0, Constants.SHOOTER_MOTOR, MotorType.kBrushless);
        m_follower = new SparkFlex(CANPort.CAN_S0, Constants.SHOOTER_FOLLOWER_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_leader.getEncoder();
        m_followerEncoder = m_follower.getEncoder();
        m_pidController = m_leader.getClosedLoopController();
        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");
        m_debouncer = new Debouncer(.1);





        m_table.put(5.99, 4150.0);
        m_table.put(5.46, 3900.0);
        m_table.put(4.58, 3700.0);
        m_table.put(4.66, 3700.0 + 225);
        m_table.put(4.5, 3600.0 + 225);
        m_table.put(4.2, 3350.0 + 225);
        m_table.put(3.75, 3250.0 + 225);
        m_table.put(3.28, 3050.0 + 225);
        m_table.put(2.79, 2850.0 + 225);
        m_table.put(MIN_DISTANCE, 2750.0);





        m_shooterAlert = new SparkMaxAlerts(m_leader, "shooterAlert");


        SparkMaxConfig leaderConfig = new SparkMaxConfig();
        leaderConfig.idleMode(IdleMode.kCoast);
        leaderConfig.smartCurrentLimit(60);
        leaderConfig.inverted(false);

        SparkMaxConfig followerConfig = new SparkMaxConfig();
        followerConfig.idleMode(IdleMode.kCoast);
        followerConfig.smartCurrentLimit(60);
        followerConfig.follow(m_leader, false
        );

        m_pidProperties = new RevPidPropertyBuilder("Shooter", false, m_leader, leaderConfig, ClosedLoopSlot.kSlot0)
            .addFF(0.0018)
            .addP(1.2e-4)
            .build();

        m_networkTableEntries.addDouble("Shooter rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Applied Output", () -> m_leader.getAppliedOutput().get());
        m_networkTableEntries.addDouble("Applied Output Follower", () -> m_follower.getAppliedOutput().get());
        m_networkTableEntries.addDouble("Velocity Follower", () -> m_followerEncoder.getVelocity().get());

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

        // TODO(2027) restore simulation once SnobotSim supports 2027
        // if (RobotBase.isSimulation()) {
        //     DCMotor gearbox = DCMotor.getNeo550(2);
        //     LinearSystem<N1, N1, N1> plant =
        //         Models.flywheelFromPhysicalConstants(gearbox, 0.01, 1.0);
        //     FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
        //     this.m_shooterSimulator = new FlywheelSimWrapper(
        //         shooterFlywheelSim,
        //         new RevMotorControllerSimWrapper(this.m_leader, gearbox),
        //         RevEncoderSimWrapper.create(this.m_leader));
        // }


        m_leader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_follower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public void clearStickyFaults() {
        m_leader.clearFaults();
        m_follower.clearFaults();
    }

    public void spinMotorForward() {
        m_leader.setThrottle(m_shooterSpeed.getValue());
    }


    public void spinMotorForward(double pow) {
        m_leader.setThrottle(pow);
    }

    public double getMinDistance() {
        return MIN_DISTANCE;
    }


    public double getRPM() {
        return m_motorEncoder.getVelocity().get();
    }

    public double getGoal() {
        return m_goal;
    }

    public double getLaunchSpeed() {
        return rpmToVelocity(m_motorEncoder.getVelocity().get());
    }

    public double rpmToVelocity(double rpm) {
        return rpm * 2 * Math.PI * Units.inchesToMeters(2) / 60 * .425;
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal, ControlType.kVelocity);

    }

    public boolean isAtGoalRPM() {
        return m_debouncer.calculate(Math.abs(m_goal - getRPM()) < DEADBAND);
    }

    public void shootFromDistance(double distance) {
        double rpm = m_table.get(distance);
        rpm += HACK_ADDITIONAL_RPM.getValue();
        setRPM(rpm);
    }

    public void shootFromDistanceBoosted(double distance) {
        double rpm = m_table.get(distance);
        setRPM(m_speedBoost.getValue() * rpm);
    }

    public double rpmFromDistance(double distance) {
        return m_table.get(distance);
    }


    public void spinMotorBackward() {
        m_leader.setThrottle(-m_shooterSpeed.getValue());
    }

    public void stop() {
        m_leader.stopMotor();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_shooterAlert.checkAlerts();
        m_pidProperties.updateIfChanged();
    }

    public void addShooterDebugCommands(boolean atComp) {
        CommandTab tab = new CommandTab("Shooter");
        if (!atComp) {
            tab.add(createShooterSpinMotorForwardCommand());
            tab.add(createShooterSpinMotorBackwardCommand());
            tab.add(createShooterSpin2000());
            tab.add(createShooterSpin1500());
        }
        tab.add(createTuneRPM());

    }

    public Command createShooterSpinMotorForwardCommand() {
        return runEnd(this::spinMotorForward, this::stop).withName("Shooter Forward! :)");
    }

    public Command createShooterSpinMotorBackwardCommand() {
        return runEnd(this::spinMotorBackward, this::stop).withName("Shooter Backward! :(");
    }

    public Command createShooterSpin1500() {
        return runEnd(() -> setRPM(1500), this::stop).withName("Shooter spins to 1500!!");
    }

    public Command createShooterSpin2000() {
        return runEnd(() -> setRPM(2000), this::stop).withName("Shooter spins to 2000!!");
    }

    public Command createTuneRPM() {
        return runEnd(() -> setRPM(m_tuneRpm.getValue()), this::stop).withName("Shooter spins to tuneRPM!!");
    }


    public Command createShootFromDistanceCommand(DoubleSupplier distanceGetter) {
        return runEnd(() -> shootFromDistance(distanceGetter.getAsDouble()), this::stop).withName("Shoot from distance");
    }


    @Override
    public void simulationPeriodic() {
        // TODO(2027) restore simulation once SnobotSim supports 2027
        // m_shooterSimulator.update();
    }


}

