// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TowerSubsystem extends SubsystemBase {
    private final CANSparkMax m_towerMotor;
    private final CANSparkMax m_towerKicker;

    /**
     * Creates a new Tower.
     */
    public TowerSubsystem() {
        m_towerMotor = new CANSparkMax(Constants.TOWER_SPARK, MotorType.kBrushless);
        m_towerKicker = new CANSparkMax(Constants.TOWER_KICKER_SPARK, MotorType.kBrushless);
        m_towerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_towerKicker.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_towerMotor.restoreFactoryDefaults();
        m_towerKicker.restoreFactoryDefaults();
        m_towerKicker.burnFlash();
        m_towerMotor.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setTowerSpeed(double speed) {
        m_towerMotor.set(speed);
    }

    public void setKickerSpeed(double speed) {
        m_towerKicker.set(speed);
    }
}
