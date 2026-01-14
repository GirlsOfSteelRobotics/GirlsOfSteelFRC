package com.gos.rebuilt.subsystems;


import com.gos.rebuilt.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkFlex m_intakeMotor;
    private final RelativeEncoder m_motorEncoder;
    private final double INTAKE_SPEED = 5;



    public IntakeSubsystem() {
        m_intakeMotor = new SparkFlex(Constants.INTAKE_MOTOR, MotorType.kBrushless);
        m_motorEncoder = m_intakeMotor.getEncoder();


    }


    public void spinMotorForward() {
        m_intakeMotor.set(INTAKE_SPEED);
    }
    public void spinMotorBackward(){
        m_intakeMotor.set(-INTAKE_SPEED);
    }
    public void stop(){
        m_intakeMotor.stopMotor();
    }
}

