package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.DriveFlag;

public class HoldChassis extends GosCommandBaseBase {

    private final Chassis m_chassis;

    public HoldChassis(Chassis chassis, DriveFlag drive) {
        addRequirements(drive);
        m_chassis = chassis;
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
        m_chassis.stopEncoders();
        m_chassis.stopJags();
    }



}
