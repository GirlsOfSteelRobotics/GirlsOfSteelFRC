package frc.robot.subsystems;

import com.gos.lib.properties.GosDoubleProperty;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class IntakeSubsystem {
    private final SparkFlex m_motor;
    private final GosDoubleProperty m_motorSpeed = new GosDoubleProperty(false, "motorSpeed", 0.75);

    public IntakeSubsystem () {
        m_motor = new SparkFlex(9, MotorType.kBrushless);

    }
    public void intake() {m_motor.set(m_motorSpeed.getValue());}
    public void outtake() {m_motor.set(-m_motorSpeed.getValue());}
    public void stop () {m_motor.stopMotor();}

}
