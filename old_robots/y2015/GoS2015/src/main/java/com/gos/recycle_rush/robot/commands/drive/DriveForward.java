package com.gos.recycle_rush.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Chassis;

public class DriveForward extends Command {
    private final Chassis m_chassis;

    public DriveForward(Chassis chassis) {
        m_chassis = chassis;
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_chassis.driveForward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
