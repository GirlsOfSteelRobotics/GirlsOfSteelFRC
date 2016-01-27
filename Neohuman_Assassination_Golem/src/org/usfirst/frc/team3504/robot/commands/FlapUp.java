package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;



public class FlapUp extends Command {

	private static final double EncoderValue = 9; //TODO: Fix this
	
    public FlapUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.flap.resetDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.flap.setTalon(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return EncoderValue == Robot.flap.getFlapEncoderDistance();   
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.flap.stopTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
