package com.gos.steam_works.commands;

import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DecrementHighShooter extends Command {

    public DecrementHighShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // doesn't need requires because that would steal shooter from shootball
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.shooter.decrementHighShooterSpeed();
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
