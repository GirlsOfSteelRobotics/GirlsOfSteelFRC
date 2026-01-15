package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkFlex m_intakeMotor;
    private final GosDoubleProperty INTAKE_SPEED = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "intakeSpeed", 1);



    public IntakeSubsystem() {
        m_intakeMotor = new SparkFlex(Constants.INTAKE_MOTOR, MotorType.kBrushless);


    }


    public void intake() {
        m_intakeMotor.set(INTAKE_SPEED.getValue());
    }
    public void outtake(){
        m_intakeMotor.set(-INTAKE_SPEED.getValue());
    }
    public void stop(){
        m_intakeMotor.stopMotor();
    }
}

