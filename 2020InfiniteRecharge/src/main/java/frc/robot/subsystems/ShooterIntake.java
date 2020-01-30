package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterIntake extends SubsystemBase {

    private final TalonSRX m_motor;

    public ShooterIntake() {
        m_motor = new TalonSRX(Constants.SHOOTER_INTAKE_TALON);
        m_motor.configFactoryDefault();
        m_motor.setInverted(false);
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
}
