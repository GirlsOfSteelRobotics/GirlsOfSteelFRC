package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Turret;

public class TurretTrackTarget extends GosCommand {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;

    private double m_difference; //How much the driver wants it to move

    public TurretTrackTarget(Turret turret, Joystick operatorJoystick) {
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
        m_turret.changeTurretOffset();
        if (Camera.foundTarget()) {
            m_turret.autoTrack();
            System.out.println("Turret Angle:  " + m_turret.getTurretAngle());
        } else {
            m_difference = m_operatorJoystick.getX() * 5.0;
            if (m_difference < -2.0 || m_difference > 2.0) {
                m_turret.setPIDSetPoint(m_turret.getTurretAngle() + m_difference);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }


}
