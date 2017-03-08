package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByDistance extends Command {

	private double rotations; 
	//Desired error limit was half an inch. 1/2 inch in ticks is approximately 150.
	private static final int ERROR_LIMIT = 150;
	
    public DriveByDistance(double inches){
    	rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI); 
    	
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.getLeftTalon().setPosition(0); 
    	Robot.chassis.getRightTalon().setPosition(0); 
    	
    	Robot.chassis.getLeftTalon().setF(0.0);
    	Robot.chassis.getRightTalon().setF(0.0);
    	
    	Robot.chassis.getLeftTalon().set(-rotations); //Move this back to execute
    	Robot.chassis.getRightTalon().set(rotations); //Move this back to execute
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.chassis.getLeftTalon().getClosedLoopError()) < ERROR_LIMIT && 
    			Math.abs(Robot.chassis.getRightTalon().getClosedLoopError()) < ERROR_LIMIT; //closedLoopError is in integers and represents ticks
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
