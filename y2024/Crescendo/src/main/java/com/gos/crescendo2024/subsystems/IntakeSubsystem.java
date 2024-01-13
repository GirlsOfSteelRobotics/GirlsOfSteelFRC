package com.gos.crescendo2024.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.GosIntProperty;
import com.gos.lib.properties.HeavyIntegerProperty;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.crescendo2024.Constants;

public class  IntakeSubsystem extends SubsystemBase {
    private static final GosDoubleProperty INTAKE_OUT_SPEED = new GosDoubleProperty(true, "Intake_Out_Speed",-1);
    private static final GosDoubleProperty INTAKE_IN_SPEED = new GosDoubleProperty(true, "Intake_In_Speed",1);
    public static final GosIntProperty INTAKE_CURRENT_LIMIT = new GosIntProperty(true, "IntakeCurrentLimit", 25);
    private final SimableCANSparkMax m_intakeMotor;
    private final RelativeEncoder m_intakeEncoder;
    private final HeavyIntegerProperty m_currentLimit;
    private final DigitalInput m_photoelectricSensor;

    public IntakeSubsystem() {
        m_intakeMotor = new SimableCANSparkMax(Constants.INTAKE_MOTOR, CANSparkLowLevel.MotorType.kBrushless);
        m_intakeMotor.restoreFactoryDefaults();
        m_intakeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_intakeMotor.setSmartCurrentLimit(10);
        m_intakeMotor.burnFlash();
        m_intakeEncoder = m_intakeMotor.getEncoder();
        m_currentLimit = new HeavyIntegerProperty(m_intakeMotor::setSmartCurrentLimit, INTAKE_CURRENT_LIMIT);
        m_photoelectricSensor = new DigitalInput(Constants.INTAKE_SENSOR);
    }

    public void intakeIn(){
        m_intakeMotor.set(INTAKE_IN_SPEED.getValue());
    }
    public void intakeOut(){
        m_intakeMotor.set(INTAKE_OUT_SPEED.getValue());
    }

    public boolean hasGamePiece(){
        return !m_photoelectricSensor.get();
    }

    //commands
    public Command createMoveIntakeInCommand() {
        return this.run(this::intakeIn).withName("IntakeSubsystemIn");
    }

    public Command createMoveIntakeOutCommand() {
        return this.run(this::intakeOut).withName("IntakeSubsystemOut");
    }










}


