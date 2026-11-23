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
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
// import org.wpilib.math.numbers.N1;
// import org.wpilib.math.system.LinearSystem;
// import org.wpilib.math.system.DCMotor;
// import org.wpilib.math.system.Models;
// import org.wpilib.framework.RobotBase;
import com.gos.lib.tunables.CommandTab;
// import org.wpilib.simulation.FlywheelSim;
import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
// import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
// import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
// import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
// import org.snobotv2.sim_wrappers.ISimWrapper;

public class FeederSubsystem extends SubsystemBase {

    private final SparkFlex m_feederMotor;
    // TODO(2027) REVLib removed SPARK conversion factors, so they are applied in software.
    //  The SPARK closed loop gains now operate on native units (rotations / RPM) and need re-tuning.
    private static final double VELOCITY_CONVERSION_FACTOR = 12;

    private final RelativeEncoder m_feederEncoder;
    private final GosDoubleProperty m_feederSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FeederSpeed", 0.8);
    private final SparkMaxAlerts m_feederMotorAlerts;
    private double m_goal;
    private static final double DEADBAND = 67;
    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;
    private final LoggingUtil m_networkTableEntries;
    // TODO(2027) restore simulation once SnobotSim supports 2027
    // private ISimWrapper m_feederSimulator;
    private final GosDoubleProperty m_tuneRpm = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FEEDAtuneRPM", 1);


    public FeederSubsystem() {

        m_networkTableEntries = new LoggingUtil("Feeder Subsystem");

        m_feederMotor = new SparkFlex(CANPort.CAN_S0, Constants.FEEDER_MOTOR, MotorType.kBrushless);

        m_feederMotorAlerts = new SparkMaxAlerts(m_feederMotor, "feedrMotor");

        SparkMaxConfig feederConfig = new SparkMaxConfig();
        feederConfig.idleMode(IdleMode.kCoast);
        feederConfig.smartCurrentLimit(60);
        feederConfig.inverted(false);



        m_feederMotor.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_pidController = m_feederMotor.getClosedLoopController();
        m_feederEncoder = m_feederMotor.getEncoder();

        m_pidProperties = new RevPidPropertyBuilder("Feeder", Constants.DEFAULT_CONSTANT_PROPERTIES, m_feederMotor, feederConfig, ClosedLoopSlot.kSlot0)
            .addFF(0)
            .addP(0)
            .build();


        // TODO(2027) restore simulation once SnobotSim supports 2027
        // if (RobotBase.isSimulation()) {
        //     DCMotor gearbox = DCMotor.getNeo550(2);
        //     LinearSystem<N1, N1, N1> plant =
        //         Models.flywheelFromPhysicalConstants(gearbox, 0.01, 1.0);
        //     FlywheelSim shooterFlywheelSim = new FlywheelSim(plant, gearbox);
        //     this.m_feederSimulator = new FlywheelSimWrapper(
        //         shooterFlywheelSim,
        //         new RevMotorControllerSimWrapper(this.m_feederMotor, gearbox),
        //         RevEncoderSimWrapper.create(this.m_feederMotor));
        // }


        m_networkTableEntries.addDouble("Current", () -> m_feederMotor.getOutputCurrent().get());

        m_networkTableEntries.addDouble("Feeder rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

    }

    public void clearStickyFaults() {
        m_feederMotor.clearFaults();
    }

    public double getGoal() {
        return m_goal;
    }

    public void feed() {
        m_feederMotor.setThrottle(m_feederSpeed.getValue());
    }

    public void reverse() {
        m_feederMotor.setThrottle(-m_feederSpeed.getValue());
    }

    public void stop() {
        m_feederMotor.stopMotor();


    }

    public boolean isAtGoalRPM() {
        return Math.abs(m_goal - getRPM()) < DEADBAND;
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal / VELOCITY_CONVERSION_FACTOR, ControlType.kVelocity);

    }

    public double getRPM() {
        return m_feederEncoder.getVelocity().get() * VELOCITY_CONVERSION_FACTOR;
    }

    @Override
    public void periodic() {
        m_feederMotorAlerts.checkAlerts();
        m_pidProperties.updateIfChanged();
        m_networkTableEntries.updateLogs();
    }

    public void addFeederDebugCommands(boolean areweatacompetitionyesornoboolean) {

        CommandTab tab = new CommandTab("Feeder");
        if (!areweatacompetitionyesornoboolean) {
            tab.add(createFeederSpin(1000));
            tab.add(createFeederSpin(500));

            tab.add(createFeederCommand());
            tab.add(createFeederReverseCommand());
            tab.add(createTuneRPM());
        }
    }

    @Override
    public void simulationPeriodic() {
        // TODO(2027) restore simulation once SnobotSim supports 2027
        // m_feederSimulator.update();
    }

    public Command createFeederCommand() {
        return runEnd(this::feed, this::stop).withName("Feed🍕😎");
    }

    public Command createFeederReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the Feeder 😱");
    }


    public Command createFeederSpin(double rpm) {
        return runEnd(() -> setRPM(rpm), this::stop).withName("Feed " + rpm + " burgers!");
    }

    public Command createTuneRPM() {
        return runEnd(() -> setRPM(m_tuneRpm.getValue()), this::stop).withName("FEEDA spins to tuneRPM!!");
    }


}
