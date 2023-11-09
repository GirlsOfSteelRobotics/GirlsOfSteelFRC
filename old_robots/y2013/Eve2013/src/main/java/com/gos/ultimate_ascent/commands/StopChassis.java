package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.DriveFlag;

public class StopChassis extends GosCommandBase {

    private final Chassis m_chassis;

    public StopChassis(Chassis chassis, DriveFlag drive) {
        m_chassis = chassis;
        addRequirements(drive);
        addRequirements(chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.stopJags();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }



}
