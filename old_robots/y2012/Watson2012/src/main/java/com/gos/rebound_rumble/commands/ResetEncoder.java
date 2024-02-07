package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class ResetEncoder extends GosCommandBaseBase {

    private final Chassis m_chassis;

    public ResetEncoder(Chassis chassis) {
        m_chassis = chassis;
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
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
