package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class DisableShooter extends GosCommandBase {
    private final Shooter m_shooter;

    public DisableShooter(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_shooter.disablePID();
        m_shooter.stopJags();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
