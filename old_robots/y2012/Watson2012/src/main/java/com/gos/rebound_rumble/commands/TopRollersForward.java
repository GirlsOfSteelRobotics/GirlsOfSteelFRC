package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class TopRollersForward extends CommandBase {

    private final Shooter m_shooter;

    public TopRollersForward(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.topRollersForward();
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
