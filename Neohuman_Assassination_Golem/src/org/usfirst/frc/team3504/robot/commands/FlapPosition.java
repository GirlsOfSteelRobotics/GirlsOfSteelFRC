package org.usfirst.frc.team3504.robot.commands;
 

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class FlapPosition extends Command {
	
	private double encoderVal = 0; //TODO: fix this
	private double speed = 0;
	
    public FlapPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoderVal = Robot.flap.getThrottle() * Robot.flap.getMaxEnc();
    	if (encoderVal > Robot.flap.getFlapEncoder())
    		speed = 1;
    	else if (encoderVal < Robot.flap.getFlapEncoder())
    		speed = -1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.flap.setTalon(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (speed > 0)
    		return encoderVal >= Robot.flap.getFlapEncoder();
    	else if (speed < 0)
    		return encoderVal <= Robot.flap.getFlapEncoder();
    	else
    		return true;
    }

    // Called once after isFinished returns true
    protected void end(){
    	Robot.flap.stopTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
