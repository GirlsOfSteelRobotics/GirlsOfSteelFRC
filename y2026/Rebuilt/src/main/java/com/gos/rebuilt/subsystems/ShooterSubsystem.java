package com.gos.rebuilt.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.rebuilt.Constants;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;

import java.util.function.DoubleSupplier;

public class ShooterSubsystem extends SubsystemBase {
    public static final Rotation2d SHOT_ANGLE = Rotation2d.fromDegrees(60);
    private static final double DEADBAND = 50;
    private static final double MIN_DISTANCE = 1.99;

    private final SparkFlex m_leader;
    private final SparkFlex m_follower;
    private final RelativeEncoder m_motorEncoder;
    private final LoggingUtil m_networkTableEntries;
    private final GosDoubleProperty m_shooterSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "shooterSpeed", 1);
    private final GosDoubleProperty m_tuneRpm = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "tuneRPM", 1);
    private final SparkMaxAlerts m_shooterAlert;

    private ISimWrapper m_shooterSimulator;
    private final InterpolatingDoubleTreeMap m_table = new InterpolatingDoubleTreeMap();
    private double m_goal;

    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;

    public static final Translation2d SHOOTER_OFFSET = new Translation2d(Units.inchesToMeters(7.72), Units.inchesToMeters(8.5));


    public ShooterSubsystem() {
        m_leader = new SparkFlex(Constants.SHOOTER_MOTOR, MotorType.kBrushless);
        m_follower = new SparkFlex(Constants.SHOOTER_FOLLOWER_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_leader.getEncoder();
        m_pidController = m_leader.getClosedLoopController();
        m_networkTableEntries = new LoggingUtil("Shooter Subsystem");

        double halfHubPlusHalfRobot = 37.25;
        m_table.put(MIN_DISTANCE, 1550.0);
        m_table.put(Units.inchesToMeters(halfHubPlusHalfRobot + 82), 4250.0);
        m_table.put(Units.inchesToMeters(halfHubPlusHalfRobot + 64), 4300.0);
        m_table.put(Units.inchesToMeters(halfHubPlusHalfRobot + 108), 4600.0);
        m_table.put(Units.inchesToMeters(halfHubPlusHalfRobot + 142), 5100.0);

        m_shooterAlert = new SparkMaxAlerts(m_leader, "shooterAlert");


        SparkMaxConfig leaderConfig = new SparkMaxConfig();
        leaderConfig.idleMode(IdleMode.kCoast);
        leaderConfig.smartCurrentLimit(60);
        leaderConfig.inverted(true);
        leaderConfig.encoder.positionConversionFactor(1);
        leaderConfig.encoder.velocityConversionFactor(1);

        SparkMaxConfig followerConfig = new SparkMaxConfig();
        followerConfig.idleMode(IdleMode.kCoast);
        followerConfig.smartCurrentLimit(60);
        followerConfig.follow(m_leader, true);

        m_pidProperties = new RevPidPropertyBuilder("Shooter", false, m_leader, leaderConfig, ClosedLoopSlot.kSlot0)
            .addFF(0)
            .addP(0)
            .build();

        m_networkTableEntries.addDouble("Shooter rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Applied Output", m_leader::getAppliedOutput);

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

        if (RobotBase.isSimulation()) {
            DCMotor gearbox = DCMotor.getNeo550(2);
            LinearSystem<N1, N1, N1> plant =
                LinearSystemId.createFlywheelSystem(gearbox, 0.01, 1.0);
            FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
            this.m_shooterSimulator = new FlywheelSimWrapper(
                shooterFlywheelSim,
                new RevMotorControllerSimWrapper(this.m_leader, gearbox),
                RevEncoderSimWrapper.create(this.m_leader));
        }


        m_leader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_follower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public void spinMotorForward() {
        m_leader.set(m_shooterSpeed.getValue());
    }


    public void spinMotorForward(double pow) {
        m_leader.set(pow);
    }

    public double getMinDistance() {
        return MIN_DISTANCE;
    }


    public double getRPM() {
        return m_motorEncoder.getVelocity();
    }

    public double getGoal() {
        return m_goal;
    }

    public double getLaunchSpeed() {
        return rpmToVelocity(m_motorEncoder.getVelocity());
    }

    public double rpmToVelocity(double rpm) {
        return rpm * 2 * Math.PI * Units.inchesToMeters(2) / 60 * .75;
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal, ControlType.kVelocity);

    }

    public boolean isAtGoalRPM() {
        return Math.abs(m_goal - getRPM()) < DEADBAND;
    }

    public void shootFromDistance(double distance) {
        double rpm = m_table.get(distance);
        setRPM(rpm);
    }

    public double rpmFromDistance(double distance) {
        return m_table.get(distance);
    }


    public void spinMotorBackward() {
        m_leader.set(-m_shooterSpeed.getValue());
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

    public void addShooterDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
        tab.add(createShooterSpinMotorForwardCommand());
        tab.add(createShooterSpinMotorBackwardCommand());
        tab.add(createShooterSpin2000());
        tab.add(createShooterSpin1500());
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
        m_shooterSimulator.update();
    }


}

