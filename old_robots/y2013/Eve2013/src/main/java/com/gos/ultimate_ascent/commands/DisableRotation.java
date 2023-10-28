package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;

public class DisableRotation extends GosCommandBase {

    private final Chassis m_chassis;

    public DisableRotation(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    public void initialize() {
        m_chassis.startAutoRotation();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return !m_chassis.isAutoRotating();
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopAutoRotation();
        m_chassis.startManualRotation();
    }



}
