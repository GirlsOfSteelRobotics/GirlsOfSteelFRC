package com.gos.stronghold.robot.commands;

import com.gos.stronghold.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class DriveBackward extends Command {

    private final Chassis m_chassis;

    public DriveBackward(Chassis chassis) {
        m_chassis = chassis;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_chassis.setTeleDriveDirection(Chassis.TeleDriveDirection.REV);
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
