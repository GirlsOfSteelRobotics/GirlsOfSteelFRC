package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class IncrementShoot extends GosCommandBase {
    private final Shooter m_shooter;
    private final OI m_oi;
    private double m_sliderValue;
    private double m_incrementValue;
    private double m_speed;

    public IncrementShoot(Shooter shooter, OI oi) {
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
        m_incrementValue = m_shooter.getIncrementValue(m_sliderValue);
        //        speed = shooter.getEncoderRate() + incrementValue;
        m_speed = m_shooter.getPIDSetPoint() + m_incrementValue;
        m_shooter.setPIDSpeed(m_speed);
        if (m_shooter.isWithinSetPoint(m_speed) && !m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersForward();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.topRollersOff();
        m_shooter.disablePID();
    }


}
