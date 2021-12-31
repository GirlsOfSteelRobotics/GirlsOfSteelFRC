package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Shooter;

public class TestShooter extends CommandBase {

    private final Shooter m_shooter;
    private double m_speed;

    public TestShooter(Shooter shooter) {
        m_shooter = shooter;
        SmartDashboard.putNumber("Shooter Jag Speed", 0.0);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
    }

    @Override
    protected void execute() {
        m_speed = SmartDashboard.getNumber("Shooter Jag Speed", 0.0);
        m_shooter.setJags(m_speed);
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_shooter.stopJags();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
