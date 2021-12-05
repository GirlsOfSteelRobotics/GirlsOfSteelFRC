package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimeDelay extends Command {

    private final double seconds;

    public TimeDelay(double seconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.seconds = seconds;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(seconds);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
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
