package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Blobs;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByVision extends Command {
	
	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
	private double dist;
	private final double SPEED_PERCENT = 0.2;

    public DriveByVision() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.blobs.distanceBetweenBlobs() == -1)
		{
    		System.out.print("DriveByVision initialize: line not in sight!!");
    		end();
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dist = Robot.blobs.distanceBetweenBlobs();
    	if (dist < Blobs.GOAL_DISTANCE)
    	{//too far --> go forward
    		leftTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
    		rightTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
    	}
    	else if (dist > Blobs.GOAL_DISTANCE)
    	{//too close --> go backward
    		leftTalon.set(ControlMode.PercentOutput, -SPEED_PERCENT);
    		rightTalon.set(ControlMode.PercentOutput, SPEED_PERCENT);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.blobs.distanceBetweenBlobs() - Blobs.GOAL_DISTANCE) < Blobs.ERROR_THRESHOLD);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
