package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class DisableChassis extends CommandBase {
    private final Chassis m_chassis;

    public DisableChassis(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
        m_chassis.stopJags();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
