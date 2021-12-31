package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Turret;

public class SetPointTurret extends CommandBase {

    private final Turret m_turret;
    private final OI m_oi;
    private double m_knobValue;

    public SetPointTurret(Turret turret, OI oi) {
        m_turret = turret;
        m_oi = oi;
        requires(m_turret);
    }

    @Override
    protected void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    protected void execute() {
        m_knobValue = m_oi.getTurretKnobValue(m_turret.TURRET_OVERRIDE_DEADZONE);
        m_turret.setPIDSetPoint(m_turret.getEncoderDistance() + m_knobValue);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_turret.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
