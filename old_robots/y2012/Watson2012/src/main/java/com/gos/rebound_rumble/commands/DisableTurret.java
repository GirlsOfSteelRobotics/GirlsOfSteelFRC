package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Turret;

public class DisableTurret extends CommandBase {
    private final Turret m_turret;

    public DisableTurret(Turret turret) {
        m_turret = turret;
        requires(m_turret);
    }

    @Override
    protected void initialize() {
        m_turret.disablePID();
        m_turret.stopJag();
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
        end();
    }

}
