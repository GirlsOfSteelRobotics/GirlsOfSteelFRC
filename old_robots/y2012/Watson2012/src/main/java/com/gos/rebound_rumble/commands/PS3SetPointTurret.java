package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Turret;

public class PS3SetPointTurret extends GosCommand {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;
    private double m_angle;

    public PS3SetPointTurret(Turret turret, Joystick operatorJoystick) {
        m_turret = turret;
        this.m_operatorJoystick = operatorJoystick;
        addRequirements(m_turret);
    }

    @Override
    public void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
    }

    @Override
    public void execute() {
        m_angle = m_operatorJoystick.getX() * 5.0;
        if (m_angle < -0.5 || m_angle > 0.5) {
            m_turret.setPIDSetPoint(m_turret.getEncoderDistance() + m_angle);
        }
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
