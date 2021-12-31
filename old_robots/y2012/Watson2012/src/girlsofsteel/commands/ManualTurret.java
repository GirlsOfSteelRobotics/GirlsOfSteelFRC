package girlsofsteel.commands;

import girlsofsteel.OI;
import girlsofsteel.subsystems.Turret;

public class ManualTurret extends CommandBase {

    private final Turret m_turret;
    private final OI m_oi;
    private double m_knobValue;
    private double m_speed;

    public ManualTurret(Turret turret, OI oi) {
        m_turret = turret;
        m_oi = oi;
        requires(m_turret);
    }

    @Override
    protected void initialize() {
        m_turret.disablePID();
    }

    @Override
    protected void execute() {
        m_knobValue = m_oi.getTurretKnobValue(Turret.TURRET_OVERRIDE_DEADZONE);
        if (m_knobValue > 0) {
            m_speed = 0.2;
        } else {
            m_speed = -0.2;
        }
        m_turret.setJagSpeed(m_speed);
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
    }
}
