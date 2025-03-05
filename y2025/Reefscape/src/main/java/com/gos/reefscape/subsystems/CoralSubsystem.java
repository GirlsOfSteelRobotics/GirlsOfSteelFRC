package com.gos.reefscape.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralSubsystem extends SubsystemBase {
    private static final GosDoubleProperty CORAL_OUT_SPEED = new GosDoubleProperty(false, "CoralOutSpeed", -0.05);
    private static final GosDoubleProperty CORAL_IN_SPEED = new GosDoubleProperty(false, "CoralInSpeed", 0.05);

    private final SparkFlex m_coralMotor;
    private final DigitalInput m_coralSensor;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_coralAlert;

    public CoralSubsystem() {
        m_coralMotor = new SparkFlex(Constants.CORAL_MOTOR_ID, MotorType.kBrushless);
        m_coralSensor = new DigitalInput(Constants.CORAL_SENSOR_ID);
        m_networkTableEntries = new LoggingUtil("Coral Subsystem");
        m_coralAlert = new SparkMaxAlerts(m_coralMotor, "Coral Motor");

        SparkMaxConfig coralConfig = new SparkMaxConfig();
        coralConfig.idleMode(IdleMode.kBrake);
        coralConfig.smartCurrentLimit(60);
        coralConfig.inverted(false);

        m_coralMotor.configure(coralConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries.addBoolean("Has Coral", this::hasCoral);
    }

    public void clearStickyFaults() {
        m_coralMotor.clearFaults();
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_coralAlert.checkAlerts();

    }

    public void coralStop() {
        m_coralMotor.set(0);
    }

    public void coralOut() {
        m_coralMotor.set(CORAL_OUT_SPEED.getValue());
    }

    public void

    coralIn() {
        m_coralMotor.set(CORAL_IN_SPEED.getValue());
    }

    public boolean hasCoral() {
        return !m_coralSensor.get();
    }



    public void addCoralDebugCommands() {
        ShuffleboardTab debugTab = Shuffleboard.getTab("Coral Debug");
        debugTab.add(createMoveCoralInCommand());
        debugTab.add(createMoveCoralOutCommand());
        debugTab.add(createIntakeUntilCoralCommand());
    }

    public Command createMoveCoralOutCommand() {
        return this.runEnd(this::coralOut, this::coralStop).withName("Coral Out");
    }

    public Command createMoveCoralInCommand() {
        return this.runEnd(this::coralIn, this::coralStop).withName("Coral In");
    }

    public Command createIntakeUntilCoralCommand() {
        return createMoveCoralOutCommand().until(this::hasCoral).withName("Intake Till Coral");
    }


}



