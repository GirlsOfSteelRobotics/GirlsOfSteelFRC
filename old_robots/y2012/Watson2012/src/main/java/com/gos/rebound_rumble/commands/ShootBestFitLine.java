package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Shooter;

public class ShootBestFitLine extends CommandBase {

    private final Joystick m_operatorJoystick;
    private final Shooter m_shooter;

    private double m_cameraDistance;

    public ShootBestFitLine(Shooter shooter, Joystick operatorJoystick) {
        m_shooter = shooter;
        m_operatorJoystick = operatorJoystick;

        requires(m_shooter);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
        m_shooter.initPID();
        m_cameraDistance = Camera.getXDistance();
    }

    @Override
    protected void execute() {
        m_shooter.autoShootBestFitLine(m_cameraDistance);
        if (Math.abs(m_operatorJoystick.getThrottle()) >= 0.3
            || Math.abs(m_operatorJoystick.getTwist()) >= 0.3) {
            m_shooter.topRollersForward();
        }
    }

    @Override
    protected boolean isFinished() {
        return !Camera.isConnected();
    }

    @Override
    protected void end() {
        m_shooter.topRollersOff();
        m_shooter.disablePID();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
