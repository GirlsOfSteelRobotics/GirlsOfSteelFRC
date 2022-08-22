package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.scra.mepi.rapid_react.Constants;

public class HopperSubsystem extends SubsystemBase {
    private final CANSparkMax m_hopperMotor;

    /** Creates a new HopperSubsystem. */
    public HopperSubsystem() {
        m_hopperMotor = new CANSparkMax(Constants.HOPPER_SPARK, MotorType.kBrushless);
        m_hopperMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
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
