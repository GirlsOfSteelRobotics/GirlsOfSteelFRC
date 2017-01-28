package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class DriveByVision extends Command {

	NetworkTable table;
	
	private static final double MAX_CURVE = 0.5; //TODO: adjust
	private static final int IMAGE_WIDTH = 320;
	

    public DriveByVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// the center of the x and y rectangles (the target)
    	double targetX = 160; //TODO: get actual center
    	
    	//if positive move right to meet target on right, if negative move left to meet target on left
    	double turnAmount = (-((IMAGE_WIDTH/2) - targetX))/(IMAGE_WIDTH/2); 
    	
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
