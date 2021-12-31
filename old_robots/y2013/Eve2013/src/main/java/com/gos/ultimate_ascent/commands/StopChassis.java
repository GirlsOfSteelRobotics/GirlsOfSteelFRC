package com.gos.ultimate_ascent.commands;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.DriveFlag;

public class StopChassis extends CommandBase {

    private final Chassis m_chassis;

    public StopChassis(Chassis chassis, DriveFlag drive) {
        m_chassis = chassis;
        requires(drive);
        requires(chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_chassis.stopJags();
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
        end();
    }

}
