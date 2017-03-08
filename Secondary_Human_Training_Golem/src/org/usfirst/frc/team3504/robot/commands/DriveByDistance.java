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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.getLeftTalon().set(-rotations);
    	Robot.chassis.getRightTalon().set(rotations); 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
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
