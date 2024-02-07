/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.ultimate_ascent.commands;


import com.gos.ultimate_ascent.subsystems.Shooter;

public class Shoot extends GosCommandBase {

    private final boolean m_camera;

    private final Shooter m_shooter;

    private double m_speed;
    private double m_time;

    public Shoot(Shooter shooter, double speed) {
        m_shooter = shooter;
        this.m_speed = speed;
        m_camera = false;
        addRequirements(shooter);
    }

    public Shoot(Shooter shooter) {
        m_camera = true;
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        if (m_camera) {
            //            speed = PositionInfo.getSpeed(ShooterCamera.getLocation());
            m_speed = 0.73;
        }
        m_shooter.initEncoder();
        m_time = timeSinceInitialized();
        //        shooter.initPID();
    }

    @Override
    public void execute() {
        if (timeSinceInitialized() - m_time > 2) {
            m_shooter.setShootTrue();
        }
        m_shooter.setJags(m_speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //        shooter.disablePID();
        m_shooter.stopJags();
        m_shooter.stopEncoder();
        m_shooter.setShootFalse();
    }


}
