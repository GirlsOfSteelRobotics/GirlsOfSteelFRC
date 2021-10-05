package com.gos.power_up.commands;

import com.gos.power_up.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SimpleDrive extends Command {

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
    public SimpleDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftTalon.set(ControlMode.PercentOutput, 0.5);
    	rightTalon.set(ControlMode.PercentOutput, 0.5);
    	System.out.println("SimpleDrive: leftA " + leftTalon.getInverted());
    	System.out.println("SimpleDrive: rightA " + rightTalon.getInverted());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftTalon.set(ControlMode.PercentOutput, 0.5);
    	rightTalon.set(ControlMode.PercentOutput, 0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
