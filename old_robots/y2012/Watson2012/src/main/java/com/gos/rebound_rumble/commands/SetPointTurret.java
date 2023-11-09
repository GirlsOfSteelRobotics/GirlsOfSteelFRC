package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Turret;

public class SetPointTurret extends GosCommand {

    private final Turret m_turret;
    private final OI m_oi;
    private double m_knobValue;

    public SetPointTurret(Turret turret, OI oi) {
        m_turret = turret;
        m_oi = oi;
        addRequirements(m_turret);
    }

    @Override
    public void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    public void execute() {
        m_knobValue = m_oi.getTurretKnobValue(Turret.TURRET_OVERRIDE_DEADZONE);
        m_turret.setPIDSetPoint(m_turret.getEncoderDistance() + m_knobValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.stopJag();
    }


}
