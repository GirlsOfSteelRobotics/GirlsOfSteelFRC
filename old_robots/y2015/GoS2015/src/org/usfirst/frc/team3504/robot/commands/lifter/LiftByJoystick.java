package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftByJoystick extends Command {

    public LiftByJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.lifter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.lifter.moveByJoystick();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // return (Robot.lifter.isAtTopLevel() || Robot.lifter.isAtTop());
        // return (Robot.lifter.isAtTop() || Robot.lifter.isAtTopLevel()); //||
        // Robot.lifter.isAtBottom());
        return false;
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
