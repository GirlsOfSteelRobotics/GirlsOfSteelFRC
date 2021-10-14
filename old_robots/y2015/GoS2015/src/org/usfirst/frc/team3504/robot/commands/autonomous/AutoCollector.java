package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoCollector extends Command {

    public AutoCollector() {
        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(2.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.collector.collectorToteIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.collector.stopCollecting();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
