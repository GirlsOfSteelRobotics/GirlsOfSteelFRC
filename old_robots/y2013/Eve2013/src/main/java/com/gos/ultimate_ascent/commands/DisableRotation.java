package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class DisableRotation extends CommandBase {

    private final Chassis m_chassis;

    public DisableRotation(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    protected void initialize() {
        m_chassis.startAutoRotation();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return !m_chassis.isAutoRotating();
    }

    @Override
    protected void end() {
        m_chassis.stopAutoRotation();
        m_chassis.startManualRotation();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
