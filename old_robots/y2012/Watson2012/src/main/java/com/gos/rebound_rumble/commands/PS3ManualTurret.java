package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.Joystick;
import com.gos.rebound_rumble.subsystems.Turret;

public class PS3ManualTurret extends GosCommandBaseBase {

    private final Turret m_turret;
    private final Joystick m_operatorJoystick;

    private double m_speed;

    public PS3ManualTurret(Turret turret, Joystick operatorJoystick) {
        m_turret = turret;
        m_operatorJoystick = operatorJoystick;
        addRequirements(m_turret);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_speed = m_operatorJoystick.getX() * .5;
        m_turret.setJagSpeed(m_speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
