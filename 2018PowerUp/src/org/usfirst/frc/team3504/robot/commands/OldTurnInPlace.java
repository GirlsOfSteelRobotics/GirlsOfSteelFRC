package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OldTurnInPlace extends Command {

	private double headingTarget;
	private double speed;
	private final static double ERROR = 2.0;
	
	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
    public OldTurnInPlace(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        headingTarget = degrees;
        speed = 0.3;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.zeroSensors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (headingTarget > 0) //send both negative
    	{
    		leftTalon.set(ControlMode.PercentOutput, -speed);
    		rightTalon.set(ControlMode.PercentOutput, -speed);
    	}
    	else
    	{
    		leftTalon.set(ControlMode.PercentOutput, speed);
    		rightTalon.set(ControlMode.PercentOutput, speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.chassis.getYaw() - headingTarget) < ERROR);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}