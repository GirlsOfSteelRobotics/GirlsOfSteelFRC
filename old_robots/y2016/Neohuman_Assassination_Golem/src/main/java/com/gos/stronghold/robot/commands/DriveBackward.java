package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.OI.DriveDirection;

/**
 *
 */
public class DriveBackward extends Command {

    private final OI m_oi;

    public DriveBackward(OI oi) {
        m_oi = oi;
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
