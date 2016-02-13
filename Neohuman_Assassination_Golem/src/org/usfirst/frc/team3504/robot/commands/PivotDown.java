package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class PivotDown extends Command {

	/*private static final double EncoderValueUp = -60; //based on initial position
	private static final double EncoderValueMiddle = -30; //TODO: fix these depending on which way is positive for motor
	private static final double EncoderValueDown = 0; //TODO: fix values
	private double encoderToUse = 0;
	*/
	
    public PivotDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pivot);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/*if (Robot.pivot.getPosition() == 1)
    		encoderToUse = EncoderValueUp;
    	else if (Robot.pivot.getPosition() == 0)
    		encoderToUse = EncoderValueMiddle;
    	else
    		encoderToUse = EncoderValueDown;

    	Robot.pivot.resetDistance();
    	*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.pivot.tiltUpandDown(-.1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return Robot.pivot.getBottomLimitSwitch() == true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pivot.tiltUpandDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
