package com.gos.crescendo2024.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.GosIntProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.crescendo2024.Constants;

public class IntakeSubsystem extends SubsystemBase {
    private static final GosDoubleProperty INTAKE_OUT_SPEED = new GosDoubleProperty(true, "Intake_Out_Speed", -1);
    private static final GosDoubleProperty INTAKE_IN_SPEED = new GosDoubleProperty(true, "Intake_In_Speed", 1);
    public static final GosIntProperty INTAKE_CURRENT_LIMIT = new GosIntProperty(true, "IntakeCurrentLimit", 25);
    private final SimableCANSparkMax m_intakeMotor;
    private final RelativeEncoder m_intakeEncoder;
    private final DigitalInput m_photoelectricSensor;
    private final LoggingUtil m_networkTableEntries;
    private final SparkMaxAlerts m_intakeAlert;

    public IntakeSubsystem() {
        m_intakeMotor = new SimableCANSparkMax(Constants.INTAKE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        m_intakeMotor.restoreFactoryDefaults();
        m_intakeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_intakeMotor.setSmartCurrentLimit(40);
        m_intakeMotor.setInverted(true);
        m_intakeMotor.burnFlash();

        m_intakeEncoder = m_intakeMotor.getEncoder();
        m_intakeAlert = new SparkMaxAlerts(m_intakeMotor, "Intake Motor");

        m_photoelectricSensor = new DigitalInput(Constants.INTAKE_SENSOR);

        m_networkTableEntries = new LoggingUtil("Intake Subsystem");
        m_networkTableEntries.addDouble("Current Velocity", m_intakeEncoder::getVelocity);
        m_networkTableEntries.addDouble("Current (Amps)", m_intakeMotor::getOutputCurrent);
    }

    @Override
    public void periodic() {
        m_networkTableEntries.updateLogs();
        m_intakeAlert.checkAlerts();
    }

    public void intakeIn() {
        m_intakeMotor.set(INTAKE_IN_SPEED.getValue());
    }

    public void intakeOut() {
        m_intakeMotor.set(INTAKE_OUT_SPEED.getValue());
    }

    public void intakeStop() {
        m_intakeMotor.set(0);
    }

    public boolean hasGamePiece() {
        return !m_photoelectricSensor.get();
    }

    public double getIntakeMotorPercentage() {
        return m_intakeMotor.getAppliedOutput();
    }
    //commands

    public Command createMoveIntakeInCommand() {
        return this.runEnd(this::intakeIn, this::intakeStop).withName("IntakeSubsystemIn");
    }

    public Command createMoveIntakeOutCommand() {
        return this.runEnd(this::intakeOut, this::intakeStop).withName("IntakeSubsystemOut");
    }
}
