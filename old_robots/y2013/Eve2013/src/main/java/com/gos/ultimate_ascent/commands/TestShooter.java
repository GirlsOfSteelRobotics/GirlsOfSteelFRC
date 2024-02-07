package com.gos.ultimate_ascent.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.subsystems.Shooter;


public class TestShooter extends GosCommandBaseBase {

    private final Shooter m_shooter;
    private double m_speed;

    public TestShooter(Shooter shooter) {
        m_shooter = shooter;
        SmartDashboard.putNumber("Speed", 0.0);
        SmartDashboard.putBoolean("Shoot", false);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_speed = SmartDashboard.getNumber("Speed", 0.0);
        if (SmartDashboard.getBoolean("Shoot", false)) {
            m_shooter.setJags(m_speed);
        } else {
            m_shooter.stopJags();
            m_shooter.setShootFalse();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopJags();
        m_shooter.stopEncoder();
    }



}
