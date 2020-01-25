package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterConveyor extends SubsystemBase {

    private final TalonSRX m_motor;

	public ShooterConveyor () {
        m_motor = new TalonSRX(Constants.SHOOTER_CONVEYOR_TALON);
        m_motor.setInverted(false);
        m_motor.configFactoryDefault();
    } 

    public void inConveyor(){
        m_motor.set(ControlMode.PercentOutput, 1);
    }

    public void outConveyor(){
        m_motor.set(ControlMode.PercentOutput, -1);
    }

    public void stop(){
        m_motor.set(ControlMode.PercentOutput, 0);
    }
}