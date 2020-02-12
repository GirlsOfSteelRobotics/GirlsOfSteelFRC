package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Winch extends SubsystemBase {

    private final WPI_TalonSRX m_motorA;
    private final WPI_TalonSRX m_motorB;

    private final NetworkTable m_customNetworkTable;

    public Winch() {
        m_motorA = new WPI_TalonSRX(Constants.WINCH_A_TALON);
        m_motorA.configFactoryDefault();
        m_motorA.setInverted(false);
        m_motorB = new WPI_TalonSRX(Constants.WINCH_B_TALON);
        m_motorB.configFactoryDefault();
        m_motorB.setInverted(false);

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Winch");
    } 

    public void wind() {
        m_motorA.set(ControlMode.PercentOutput, 1);
        m_motorB.set(ControlMode.PercentOutput, 1);
    }

    public void unwind() {
        m_motorA.set(ControlMode.PercentOutput, -1);
        m_motorB.set(ControlMode.PercentOutput, -1);
    }

    public void periodic() {
        m_customNetworkTable.getEntry("Speed").setDouble(m_motorA.getMotorOutputPercent());
    }

    public void stop() {
        m_motorA.set(ControlMode.PercentOutput, 0);
        m_motorB.set(ControlMode.PercentOutput, 0);
    }

}
