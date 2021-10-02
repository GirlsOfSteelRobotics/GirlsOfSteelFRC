package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase {

    private final WPI_TalonSRX m_motor;

    private final NetworkTable m_customNetworkTable;

    public Lift() {
        m_motor = new WPI_TalonSRX(Constants.LIFT_TALON);
        m_motor.configFactoryDefault();
        m_motor.setInverted(true);

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Lift");
    } 

    public void liftUp() {
        m_motor.set(ControlMode.PercentOutput, 0.8);
    }

    public void liftDown() {
        m_motor.set(ControlMode.PercentOutput, -0.5);
    }

    @Override
    public void periodic() {
        m_customNetworkTable.getEntry("Speed").setDouble(m_motor.getMotorOutputPercent());
    }

    public void stop() {
        m_motor.set(ControlMode.PercentOutput, 0);
    }

}
