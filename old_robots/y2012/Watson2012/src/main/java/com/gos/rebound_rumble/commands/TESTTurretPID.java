package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Turret;

public class TESTTurretPID extends CommandBase {

    private final Turret m_turret;

    private double m_p;
    private double m_d;

    private double m_setpoint;

    public TESTTurretPID(Turret turret) {
        m_turret = turret;
        requires(m_turret);
        SmartDashboard.putNumber("Turret Setpoint", 0.0);
        SmartDashboard.putNumber("Turret P", 0.0);
        SmartDashboard.putNumber("Turret D", 0.0);
    }

    @Override
    protected void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    protected void execute() {
        m_p = SmartDashboard.getNumber("Turret P", 0.0);
        m_d = SmartDashboard.getNumber("Turret D", 0.0);
        m_turret.setPDs(m_p, m_d);
        m_setpoint = SmartDashboard.getNumber("Turret Setpoint", 0.0);
        m_turret.setPIDSetPoint(m_setpoint);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_turret.disablePID();
        m_turret.stopJag();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
