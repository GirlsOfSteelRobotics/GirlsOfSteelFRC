package org.usfirst.frc.team3504.robot.commands.tests;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDLifterTesting extends Command {

    public PIDLifterTesting() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.lifter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.lifter.tunePID();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (Robot.lifter.isAtPosition());
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.lifter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
