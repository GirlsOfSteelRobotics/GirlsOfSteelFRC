package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class DisableChassis extends GosCommandBase {
    private final Chassis m_chassis;

    public DisableChassis(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.stopJags();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
