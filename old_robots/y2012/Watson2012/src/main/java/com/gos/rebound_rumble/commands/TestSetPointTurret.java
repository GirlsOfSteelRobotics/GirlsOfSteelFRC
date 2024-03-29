package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebound_rumble.subsystems.Turret;

public class TestSetPointTurret extends GosCommandBase {

    private final Turret m_turret;
    private double m_angle;

    public TestSetPointTurret(Turret turret) {
        m_turret = turret;
        addRequirements(m_turret);
        SmartDashboard.putNumber("Turret Relative Angle", 0.0);
    }

    @Override
    public void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    public void execute() {
        m_angle = SmartDashboard.getNumber("Turret Relative Angle", 0.0);
        m_turret.setPIDSetPoint(m_turret.getEncoderDistance() + m_angle);
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
