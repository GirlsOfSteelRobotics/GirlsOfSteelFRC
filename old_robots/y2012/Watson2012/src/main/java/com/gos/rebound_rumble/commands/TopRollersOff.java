package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Shooter;

public class TopRollersOff extends CommandBase {
    private final Shooter m_shooter;

    public TopRollersOff(Shooter shooter) {
        m_shooter = shooter;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_shooter.topRollersOff();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_shooter.topRollersOff();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
