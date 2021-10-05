package com.gos.steam_works.commands;

import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IncrementHighShooter extends Command {

    public IncrementHighShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // doesn't need requires because that would steal shooter from shootball
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.shooter.incrementHighShooterSpeed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
