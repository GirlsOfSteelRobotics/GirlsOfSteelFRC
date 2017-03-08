package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByDistance extends Command {

	private double rotations; 
	//Desired error limit was half an inch. 1/2 inch in ticks is approximately 150.
	private static final int ERROR_LIMIT = 150;
	
	private CANTalon leftTalon = Robot.chassis.getLeftTalon();
	private CANTalon rightTalon = Robot.chassis.getRightTalon();
	
    public DriveByDistance(double inches){
    	rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI); 
    	
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftTalon.changeControlMode(TalonControlMode.Position); //TODO: check talon control mode, should be okay
    	rightTalon.changeControlMode(TalonControlMode.Position);
    	
    	Robot.chassis.setupFPID(leftTalon);
    	Robot.chassis.setupFPID(rightTalon);
    	
    	leftTalon.setF(0.0);
    	rightTalon.setF(0.0);
    	
    	leftTalon.set(-rotations); //Move this back to execute
    	rightTalon.set(rotations); //Move this back to execute
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
