package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterIntake extends SubsystemBase {

    private final TalonSRX m_motor;
    private final DoubleSolenoid m_piston;

    public ShooterIntake() {
        m_motor = new TalonSRX(Constants.SHOOTER_INTAKE_TALON);
        m_motor.configFactoryDefault();
        m_motor.setInverted(false);
        m_piston = new DoubleSolenoid(Constants.DOUBLE_SOLENOID_SHOOTER_INTAKE_FORWARD, Constants.DOUBLE_SOLENOID_SHOOTER_INTAKE_BACKWARD);
    } 

    public void collectCells() {
        m_motor.set(ControlMode.PercentOutput, 1);
    }

    public void decollectCells() {
        m_motor.set(ControlMode.PercentOutput, -1);
    }

    public void stop() {
        m_motor.set(ControlMode.PercentOutput, 0);
    }

    public void moveToCollect() {
        m_piston.set(Value.kForward);
    }

    public void retract() {
        m_piston.set(Value.kReverse);
    }
}
