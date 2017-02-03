package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class DriveByVision extends Command {

	NetworkTable table;
	
	private static final double MAX_CURVE = 0.5; //TODO: adjust
	private static final int IMAGE_WIDTH = 320;
	double[] defaultValue = new double[0];
	
	
	//width of X or Y in pixels when the robot is at the lift
	private static final double GOAL_WIDTH = 30; //TODO: test and change
	
	private static double encDist;
	private static double lastEncDist;
	
	private static final double MIN_DIST = 0.5;
	
    public DriveByVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Change more to Percent Vbus
    	Robot.chassis.driveLeftA.changeControlMode(TalonControlMode.PercentVbus);
    	Robot.chassis.driveRightA.changeControlMode(TalonControlMode.PercentVbus);
    	
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	    double[] centerX = table.getNumberArray("centerX", defaultValue);
		double[] centerY = table.getNumberArray("centerY", defaultValue);
		double[] height = table.getNumberArray("height", defaultValue);
		double[] width = table.getNumberArray("width", defaultValue);
		
		//double trouble = 100;
		lastEncDist = encDist;
		encDist = Robot.chassis.getEncoderDistance();
		
		// the center of the x and y rectangles (the target)
    	double targetX = (centerX[0] + centerY[0])/2.0;
    	double rotateValue;
    	if(centerX.length != 2)
    		rotateValue = 0;
    	else
    	{	
    		//if positive move right to meet target on right, if negative move left to meet target on left
    		rotateValue = ((targetX - (IMAGE_WIDTH/2)))/(IMAGE_WIDTH/2); 
    	}
    	
    	Robot.chassis.drive(.1, rotateValue); //TODO: change moveValue
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(encDist - lastEncDist) <= MIN_DIST);
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
