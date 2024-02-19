package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.subsystems.Chassis;

public class HoldPosition extends GosCommandBaseBase {
    private final Chassis m_chassis;

    public HoldPosition(Chassis chassis) {
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
        m_chassis.initEncoders();
        m_chassis.initHoldPosition();
    }

    @Override
    public void execute() {
        m_chassis.holdPosition();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.disablePositionPIDs();
        m_chassis.endEncoders();
        m_chassis.stopJags();
    }



}
