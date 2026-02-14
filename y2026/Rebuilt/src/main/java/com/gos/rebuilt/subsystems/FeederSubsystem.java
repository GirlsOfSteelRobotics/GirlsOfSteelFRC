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
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {

    private final SparkFlex m_feederMotor;
    private final RelativeEncoder m_feederEncoder;
    private final GosDoubleProperty m_feederSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "FeederSpeed", 1);
    private final SparkMaxAlerts m_feederMotorAlerts;
    private double m_goal;
    private static final double DEADBAND = 2;
    private final SparkClosedLoopController m_pidController;
    private final PidProperty m_pidProperties;
    private final LoggingUtil m_networkTableEntries;


    public FeederSubsystem() {

        m_networkTableEntries = new LoggingUtil("Feeder Subsystem");

        m_feederMotor = new SparkFlex(Constants.FEEDER_MOTOR, MotorType.kBrushless);

        m_feederMotorAlerts = new SparkMaxAlerts(m_feederMotor, "feedrMotor");

        SparkMaxConfig feederConfig = new SparkMaxConfig();
        feederConfig.idleMode(IdleMode.kCoast);
        feederConfig.smartCurrentLimit(60);
        feederConfig.inverted(true);


        m_feederMotor.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_pidController = m_feederMotor.getClosedLoopController();
        m_feederEncoder = m_feederMotor.getEncoder();

        m_pidProperties = new RevPidPropertyBuilder("Feeder", false, m_feederMotor, feederConfig, ClosedLoopSlot.kSlot0)
            .addFF(0)
            .addP(0)
            .build();


        m_networkTableEntries.addDouble("Current", m_feederMotor::getOutputCurrent);

        m_networkTableEntries.addDouble("Pizza rpm", this::getRPM);

        m_networkTableEntries.addBoolean("at goal", this::isAtGoalRPM);

        m_networkTableEntries.addDouble("Goal velocity", this::getGoal);

    }

    public double getGoal() {
        return m_goal;
    }

    public void feed() {
        m_feederMotor.set(m_feederSpeed.getValue());
    }

    public void reverse() {
        m_feederMotor.set(-m_feederSpeed.getValue());
    }

    public void stop() {
        m_feederMotor.stopMotor();


    }

    public boolean isAtGoalRPM() {
        return Math.abs(m_goal - getRPM()) < DEADBAND;
    }

    public void setRPM(double goal) {
        m_goal = goal;
        m_pidController.setSetpoint(goal, ControlType.kVelocity);

    }

    public double getRPM() {
        return m_feederEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        m_feederMotorAlerts.checkAlerts();
        m_pidProperties.updateIfChanged();
        m_networkTableEntries.updateLogs();
    }

    public void addFeederDebugCommands() {
        ShuffleboardTab tab = Shuffleboard.getTab("Feeder");
        tab.add(createFeederCommand());
        tab.add(createFeederReverseCommand());
        tab.add(createFeederSpin1000());
        tab.add(createFeederSpin500());
    }

    public Command createFeederCommand() {
        return runEnd(this::feed, this::stop).withName("Feed🍕😎");
    }

    public Command createFeederReverseCommand() {
        return runEnd(this::reverse, this::stop).withName("Reverse the Feeder 😱");
    }

    public Command createFeederSpin1000() {
        return runEnd(() -> setRPM(1000), this::stop).withName("Feed 1000 burgers!");
    }

    public Command createFeederSpin500() {
        return runEnd(() -> setRPM(500), this::stop).withName("Feed 500 donuts!");
    }

}
