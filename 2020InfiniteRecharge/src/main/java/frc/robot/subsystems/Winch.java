package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Winch extends SubsystemBase {

    private final CANSparkMax m_motorA;
    private final CANSparkMax m_motorB;
    private final CANEncoder m_encoder;

    private final NetworkTable m_customNetworkTable;

    public Winch() {
        m_motorA = new CANSparkMax(Constants.WINCH_A_SPARK, MotorType.kBrushed);
        m_motorA.setIdleMode(IdleMode.kBrake);
        m_motorA.setInverted(false);
        m_encoder = m_motorA.getEncoder(EncoderType.kQuadrature, 8192);
        m_motorB = new CANSparkMax(Constants.WINCH_B_SPARK, MotorType.kBrushed);
        m_motorB.setIdleMode(IdleMode.kBrake);
        m_motorB.setInverted(false);

        m_customNetworkTable = NetworkTableInstance.getDefault().getTable("SuperStructure/Winch");
    } 

    public void wind() {
        m_motorA.set(0.8);
        m_motorB.set(0.8);
    }

    public void unwind() {
        m_motorA.set(-0.8);
        m_motorB.set(-0.8);
    }

    public void periodic() {
        m_customNetworkTable.getEntry("Speed").setDouble(m_motorA.get());
    }

    public void stop() {
        m_motorA.set(0);
        m_motorB.set(0);
    }

}
