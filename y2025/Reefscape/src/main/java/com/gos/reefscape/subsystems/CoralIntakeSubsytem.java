package com.gos.reefscape.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralIntakeSubsytem extends SubsystemBase {
    private final SparkFlex m_intakeMotor;
    private final LoggingUtil m_networkTableEntries;
    private final DigitalInput m_intakeSensor;
    private final SparkMaxAlerts m_checkAlerts;

    public CoralIntakeSubsytem() {
        m_intakeMotor = new SparkFlex(Constants.CORAL_INTAKE_MOTOR_ID, MotorType.kBrushless);
        m_intakeSensor = new DigitalInput(Constants.INTAKE_SENSOR_ID);

        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.idleMode(IdleMode.kBrake);
        intakeConfig.smartCurrentLimit(60);
        intakeConfig.inverted(false);

        m_intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries = new LoggingUtil("Coral Intake");
        m_networkTableEntries.addBoolean("Has Piece", this::hasCoral);

        m_checkAlerts = new SparkMaxAlerts(m_intakeMotor, "Coral Intake Alert");

    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_checkAlerts.checkAlerts();
    }

    public void intakeIn() {

        m_intakeMotor.set(1);

    }

    public void intakeOut() {
        m_intakeMotor.set(-1);
    }

    public boolean hasCoral() {
        return m_intakeSensor.get();
    }

    public void intakeStop() {
        m_intakeMotor.set(0);
    }


    // Command Factories
    public Command createIntakeInCommand() {
        return runEnd(this::intakeIn, this::intakeStop).withName("Intake IN");
    }

    public Command createIntakeOutCommand() {
        return runEnd(this::intakeOut, this::intakeStop).withName("Intake OUT :(");
    }

    public Command createIntakeUntilCoral() {
        return runEnd(this::intakeIn, this::intakeStop).until(this::hasCoral).withName("intake until coral :)");
    }
}
