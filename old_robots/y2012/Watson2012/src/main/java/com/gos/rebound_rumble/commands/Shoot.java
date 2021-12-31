package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class Shoot extends CommandBase {

    private final Shooter m_shooter;
    private final OI m_oi;
    private final Joystick m_operatorJoystick;

    private final double m_speed;

    public Shoot(Shooter shooter, OI oi, double speed) {
        this.m_speed = speed;
        m_shooter = shooter;
        m_oi = oi;
        m_operatorJoystick = m_oi.getOperatorJoystick();
        requires(m_shooter);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    protected void execute() {
        m_shooter.shoot(m_speed);
        if (Math.abs(m_operatorJoystick.getThrottle()) >= 0.3 ||
            Math.abs(m_operatorJoystick.getTwist()) >= 0.3) {
            m_shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
