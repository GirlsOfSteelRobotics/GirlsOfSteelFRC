package com.gos.infinite_recharge.subsystems;

import com.gos.infinite_recharge.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SimableCANSparkMax;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Winch extends SubsystemBase {

    private final SimableCANSparkMax m_motorA;
    private final SimableCANSparkMax m_motorB;
    private final RelativeEncoder m_encoder;

    private final NetworkTable m_customNetworkTable;

    public Winch(boolean isBrushed) {
        m_motorA = new SimableCANSparkMax(Constants.WINCH_A_SPARK, isBrushed ? MotorType.kBrushed : MotorType.kBrushless);
        m_motorA.restoreFactoryDefaults();
        m_motorA.setIdleMode(IdleMode.kBrake);
        m_motorA.setInverted(false);
        if (isBrushed) {
            m_encoder = m_motorA.getEncoder();
        } else {
            m_encoder = m_motorA.getEncoder();
        }

        m_motorB = new SimableCANSparkMax(Constants.WINCH_B_SPARK, isBrushed ? MotorType.kBrushed : MotorType.kBrushless);
        m_motorB.restoreFactoryDefaults();
        m_motorB.setIdleMode(IdleMode.kBrake);
        m_motorB.setInverted(false);

        m_motorA.burnFlash();
        m_motorB.burnFlash();

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

    @Override
    public void periodic() {
        m_customNetworkTable.getEntry("Speed").setDouble(m_motorA.get());
        m_customNetworkTable.getEntry("Height").setDouble(m_encoder.getPosition());
    }

    public void stop() {
        m_motorA.set(0);
        m_motorB.set(0);
    }

}
