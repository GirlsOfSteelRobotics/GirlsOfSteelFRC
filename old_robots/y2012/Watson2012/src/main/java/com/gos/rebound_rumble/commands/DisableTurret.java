package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Turret;

public class DisableTurret extends CommandBase {
    private final Turret m_turret;

    public DisableTurret(Turret turret) {
        m_turret = turret;
        addRequirements(m_turret);
    }

    @Override
    public void initialize() {
        m_turret.stopJag();
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
