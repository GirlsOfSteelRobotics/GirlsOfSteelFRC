package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class Intake {
    private SparkFlex m_motor;
    public Intake () {
        m_motor = new SparkFlex(9, MotorType.kBrushless);

    }
    public void intake() {m_motor.set(8);}
    public void outtake() {m_motor.set(2);}
    public void stop () {m_motor.stopMotor();}
}
