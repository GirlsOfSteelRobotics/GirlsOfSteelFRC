package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class ToggleGyro extends GosCommandBaseBase {

    private final Chassis m_chassis;

    public ToggleGyro(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    public void initialize() {
        if (m_chassis.isGyroEnabled()) {
            m_chassis.stopGyro();
        } else {
            m_chassis.resetGyro();
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
