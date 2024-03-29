package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;


public class PS3ShootUsingJoystick extends GosCommandBase {

    private final Shooter m_shooter;
    private final OI m_oi;
    private final Joystick m_operatorJoystick;

    private double m_speed;

    public PS3ShootUsingJoystick(Shooter shooter, OI oi) {
        m_shooter = shooter;
        m_operatorJoystick = oi.getOperatorJoystick();
        m_oi = oi;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
    }

    @Override
    public void execute() {
        m_speed = Math.abs(m_operatorJoystick.getZ()) * 40.0;
        m_shooter.shootUsingBallVelocity(m_speed);
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
        if (!m_oi.areTopRollersOverriden()) {
            m_shooter.topRollersOff();
        }
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }



}
