package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Winch extends SubsystemBase {

    private final TalonSRX m_motorA;
    private final TalonSRX m_motorB;

    public Winch() {
        m_motorA = new TalonSRX(Constants.WINCH_A_TALON);
        m_motorA.configFactoryDefault();
        m_motorA.setInverted(false);
        m_motorB = new TalonSRX(Constants.WINCH_B_TALON);
        m_motorB.configFactoryDefault();
        m_motorB.setInverted(false);
    } 

    public void wind() {
        m_motorA.set(ControlMode.PercentOutput, 1);
        m_motorB.set(ControlMode.PercentOutput, 1);
    }

    public void unwind() {
        m_motorA.set(ControlMode.PercentOutput, -1);
        m_motorB.set(ControlMode.PercentOutput, -1);
    }

    public void stop() {
        m_motorA.set(ControlMode.PercentOutput, 0);
        m_motorB.set(ControlMode.PercentOutput, 0);
    }

}
