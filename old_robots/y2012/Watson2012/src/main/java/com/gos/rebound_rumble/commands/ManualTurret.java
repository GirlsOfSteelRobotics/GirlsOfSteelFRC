package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Turret;

public class ManualTurret extends CommandBase {

    private final Turret m_turret;
    private final OI m_oi;
    private double m_knobValue;
    private double m_speed;

    public ManualTurret(Turret turret, OI oi) {
        m_turret = turret;
        m_oi = oi;
        addRequirements(m_turret);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_knobValue = m_oi.getTurretKnobValue(Turret.TURRET_OVERRIDE_DEADZONE);
        if (m_knobValue > 0) {
            m_speed = 0.2;
        } else {
            m_speed = -0.2;
        }
        m_turret.setJagSpeed(m_speed);
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
