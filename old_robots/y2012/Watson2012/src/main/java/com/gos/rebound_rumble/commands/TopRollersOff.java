package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class TopRollersOff extends GosCommand {
    private final Shooter m_shooter;

    public TopRollersOff(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.topRollersOff();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.topRollersOff();
    }



}
