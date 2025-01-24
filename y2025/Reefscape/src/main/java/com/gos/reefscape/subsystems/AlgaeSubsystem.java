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

public class AlgaeSubsystem extends SubsystemBase {


    private final SparkFlex m_algaeMotor;
    private final DigitalInput m_algaeSensor;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_algaeAlert;

    public AlgaeSubsystem() {
        m_algaeMotor = new SparkFlex(Constants.ALGAE_MOTOR_ID, MotorType.kBrushless);
        m_algaeSensor = new DigitalInput(Constants.ALGAE_SENSOR_ID);
        m_networkTableEntries = new LoggingUtil("Algae Intake Subsytem");
        m_algaeAlert = new SparkMaxAlerts(m_algaeMotor, "Algae Intake Motor");

        SparkMaxConfig algaeMotorConfig = new SparkMaxConfig();
        algaeMotorConfig.idleMode(IdleMode.kBrake);
        algaeMotorConfig.smartCurrentLimit(60);
        algaeMotorConfig.inverted(true);
        m_algaeMotor.configure(algaeMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        m_networkTableEntries.addBoolean("Has Algae", this::hasAlgae);

    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_algaeAlert.checkAlerts();

    }


    public void intakeStop() {
        m_algaeMotor.set(0);
    }

    public void algaeIntakeOut() {
        m_algaeMotor.set(-1);
    }

    public void algaeIntakeIn() {
        m_algaeMotor.set(1);
    }

    public boolean hasAlgae() {
        return !m_algaeSensor.get();
    }

    public Command createMoveIntakeOutCommand() {
        return this.runEnd(this::algaeIntakeOut, this::intakeStop).withName("Intake Out");
    }

    public Command createIntakeUntilAlgaeCommand() {
        return createMoveIntakeInCommand().until(this::hasAlgae).withName("Intake Till Piece");
    }

    public Command createMoveIntakeInCommand() {
        return this.runEnd(this::algaeIntakeIn, this::intakeStop).withName("Intake In");
    }
}

