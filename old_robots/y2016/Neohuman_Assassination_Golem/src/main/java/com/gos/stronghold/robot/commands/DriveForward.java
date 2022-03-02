package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.OI.DriveDirection;

/**
 *
 */
public class DriveForward extends CommandBase {

    private final OI m_oi;

    public DriveForward(OI oi) {
        m_oi = oi;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_oi.setDriveDirection(DriveDirection.kFWD);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {

    }


}
