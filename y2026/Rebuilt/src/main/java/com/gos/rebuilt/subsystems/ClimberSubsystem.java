package com.gos.rebuilt.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final SparkFlex m_climberMotor;
    private final RelativeEncoder m_climberEncoder;
    private final double CLIMBING_SPEED = 5;

    public ClimberSubsystem(){
        m_climberMotor = new SparkFlex(2, MotorType.kBrushless);
        m_climberEncoder = m_climberMotor.getEncoder();
    }

    public void climbUp(){
        m_climberMotor.set(CLIMBING_SPEED);
    }
    public void climbDown(){
        m_climberMotor.set(-CLIMBING_SPEED);
    }
    public void stop(){
        m_climberMotor.stopMotor();
    }
    public void climbToHeight(double height){

    }
}
