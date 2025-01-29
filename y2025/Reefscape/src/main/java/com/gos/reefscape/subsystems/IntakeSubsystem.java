package com.gos.reefscape.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.reefscape.Constants;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {


    private final SparkFlex m_intakeMotor;
    private final DigitalInput m_intakeSensor;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_intakeAlert;

    public IntakeSubsystem() {
        m_intakeMotor = new SparkFlex(Constants.INTAKE_MOTOR_ID, MotorType.kBrushless);
        m_intakeSensor = new DigitalInput(Constants.INTAKE_SENSOR_ID);
        m_networkTableEntries = new LoggingUtil("Intake Subsystem");
        m_intakeAlert = new SparkMaxAlerts(m_intakeMotor, "Intake Motor");

        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.idleMode(IdleMode.kBrake);
        intakeConfig.smartCurrentLimit(60);
        intakeConfig.inverted(true);
        m_intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries.addBoolean("Has Coral", this::hasCoral);

    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_intakeAlert.checkAlerts();

    }


    public void intakeStop() {
        m_intakeMotor.set(0);
    }

    public void intakeOut() {
        m_intakeMotor.set(-1);
    }

    public void intakeIn() {
        m_intakeMotor.set(1);
    }


    public boolean hasCoral() {
        return !m_intakeSensor.get();
    }

    public Command createMoveIntakeOutCommand() {
        return this.runEnd(this::intakeOut, this::intakeStop).withName("Intake Out");
    }

    public Command createIntakeUntilCoralCommand() {
        return createMoveIntakeInCommand().until(this::hasCoral).withName("Intake Till Piece");
    }



    public Command createMoveIntakeInCommand() {
        return this.runEnd(this::intakeIn, this::intakeStop).withName("Intake In");
    }
}

