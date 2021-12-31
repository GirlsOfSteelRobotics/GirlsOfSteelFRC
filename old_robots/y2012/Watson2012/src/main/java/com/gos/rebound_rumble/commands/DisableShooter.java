package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class DisableShooter extends CommandBase {
    private final Shooter m_shooter;

    public DisableShooter(Shooter shooter) {
        m_shooter = shooter;
        requires(m_shooter);
    }

    @Override
    protected void initialize() {
        m_shooter.disablePID();
        m_shooter.stopJags();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
