package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Turret;

public class TESTTurret extends CommandBase {

    private final Turret m_turret;

    private double m_speed;

    private double m_pulses;
    private boolean m_inDegrees;

    public TESTTurret(Turret turret) {
        m_turret = turret;
        requires(m_turret);
        SmartDashboard.putNumber("Turret Jags Speed", 0.0);
        SmartDashboard.putNumber("Turret Encoder Pulses", 0.0);
        SmartDashboard.putBoolean("Turret Encoder in Degrees", false);
    }

    @Override
    protected void initialize() {
        m_turret.disablePID();
    }

    @Override
    protected void execute() {
        m_speed = SmartDashboard.getNumber("Turret Jags Speed", 0.0);
        m_turret.setJagSpeed(m_speed);
        m_pulses = SmartDashboard.getNumber("Turret Encoder Pulses", 0.0);
        m_inDegrees = SmartDashboard.getBoolean("Turret Encoder in Degrees", false);
        m_turret.setEncoderUnit(m_pulses, m_inDegrees);
        SmartDashboard.putNumber("Turret Encoder", m_turret.getEncoderDistance());
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
