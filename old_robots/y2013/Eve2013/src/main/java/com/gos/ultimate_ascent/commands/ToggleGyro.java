package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class ToggleGyro extends CommandBase {

    private final Chassis m_chassis;

    public ToggleGyro(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    protected void initialize() {
        if (m_chassis.isGyroEnabled()) {
            m_chassis.stopGyro();
        } else {
            m_chassis.resetGyro();
        }
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
