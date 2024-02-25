package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class ManualShoot extends GosCommandBase {

    private final Shooter m_shooter;
    private final OI m_oi;
    private double m_sliderValue;
    private double m_shooterSpeed;

    public ManualShoot(Shooter shooter, OI oi) {
        m_oi = oi;
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        m_sliderValue = m_oi.getShooterSliderValue();
        m_shooterSpeed = m_shooter.manualShooterSpeedConverter(m_sliderValue);
        m_shooter.setPIDSpeed(m_shooterSpeed);
        if (m_shooter.isWithinSetPoint(m_shooterSpeed) && !m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersForward();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }



}
