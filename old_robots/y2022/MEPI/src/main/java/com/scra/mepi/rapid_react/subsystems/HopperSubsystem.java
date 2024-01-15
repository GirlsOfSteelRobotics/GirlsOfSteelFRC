package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HopperSubsystem extends SubsystemBase {
    private final CANSparkMax m_hopperMotor;

    /**
     * Creates a new HopperSubsystem.
     */
    public HopperSubsystem() {
        m_hopperMotor = new CANSparkMax(Constants.HOPPER_SPARK, MotorType.kBrushless);
        m_hopperMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_hopperMotor.setSmartCurrentLimit(30);
        m_hopperMotor.restoreFactoryDefaults();
        m_hopperMotor.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setHopperSpeed(double speed) {
        m_hopperMotor.set(speed);
    }
}
