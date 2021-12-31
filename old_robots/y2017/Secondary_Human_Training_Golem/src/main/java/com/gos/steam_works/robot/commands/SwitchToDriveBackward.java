package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.steam_works.robot.OI;
import com.gos.steam_works.robot.OI.DriveDirection;
import com.gos.steam_works.robot.subsystems.Chassis;

/**
 *
 */
public class SwitchToDriveBackward extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public SwitchToDriveBackward(OI oi, Chassis chassis) {
        m_oi = oi;
        m_chassis = chassis;
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_oi.setDriveDirection(DriveDirection.kREV);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
