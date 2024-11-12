// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SimableCANSparkMax;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TowerSubsystem extends SubsystemBase {
    private final SimableCANSparkMax m_towerMotor;
    private final SimableCANSparkMax m_towerKicker;
    private final DigitalInput m_beamBreak = new DigitalInput(9);

    /**
     * Creates a new Tower.
     */
    public TowerSubsystem() {
        m_towerMotor = new SimableCANSparkMax(Constants.TOWER_SPARK, MotorType.kBrushless);
        m_towerKicker = new SimableCANSparkMax(Constants.TOWER_KICKER_SPARK, MotorType.kBrushless);
        m_towerMotor.restoreFactoryDefaults();
        m_towerKicker.restoreFactoryDefaults();
        m_towerMotor.setSmartCurrentLimit(30);
        m_towerKicker.setSmartCurrentLimit(30);
        m_towerMotor.setIdleMode(IdleMode.kBrake);
        m_towerKicker.setIdleMode(IdleMode.kCoast);

        m_towerKicker.burnFlash();
        m_towerMotor.burnFlash();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putBoolean("beam break", m_beamBreak.get());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setTowerSpeed(double speed) {
        m_towerMotor.set(speed);
    }

    public boolean getBeamBreak() {
        return m_beamBreak.get();
    }

    public void setKickerSpeed(double speed) {
        m_towerKicker.set(speed);
    }
}
