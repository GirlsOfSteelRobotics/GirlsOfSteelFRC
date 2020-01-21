package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterIntake extends SubsystemBase {

    private final CANSparkMax m_motor;

	public ShooterIntake () {
        m_motor = new CANSparkMax(Constants.SHOOTER_INTAKE_SPARK, MotorType.kBrushless);
    } 

    public void collectCells(){
        m_motor.set(1);
    }

    public void stop(){
        m_motor.set(0);
    }
}