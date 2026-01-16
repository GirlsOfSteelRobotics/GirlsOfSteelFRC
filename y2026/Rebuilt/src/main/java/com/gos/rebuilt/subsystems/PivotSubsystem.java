package com.gos.rebuilt.subsystems;


import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotSubsystem extends SubsystemBase {

    private final SparkFlex m_pivotMotor;
    private final GosDoubleProperty m_pivotSpeed = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "pivotSpeed", 1);


    public PivotSubsystem() {
        m_pivotMotor = new SparkFlex(Constants.PIVOT_MOTOR, MotorType.kBrushless);

    }

    public void setSpeed(double pow) {
        m_pivotMotor.set(pow);
    }

    public void stop() {
        m_pivotMotor.stopMotor();
    }



}

