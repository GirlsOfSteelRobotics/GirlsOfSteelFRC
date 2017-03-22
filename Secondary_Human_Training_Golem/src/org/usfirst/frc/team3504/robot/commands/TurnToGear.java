package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class TurnToGear extends Command {
	
	NetworkTable table;
	double[] defaultValue = new double[0];
	//private static final double IMAGE_CENTER = IMAGE_WIDTH/2.0;
	//private static final double TOLERANCE = 5; //TODO: test this (in pixels)
	
	double[] centerX = new double[2];
	//private double currentX;

	public enum Direction {kLeft, kRight}; 
	private Direction direction; 
	
    public TurnToGear(Direction direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	this.direction = direction; 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    	
		centerX = table.getNumberArray("centerX", defaultValue);
		
		if (direction == Direction.kRight){
			Robot.chassis.turn(0.1); //TODO: test
		} else if (direction == Direction.kLeft){
			Robot.chassis.turn(-0.1);
		}
		
		
    	/*if(centerX.length == 2) {
    		currentX = (centerX[0] + centerX[1])/2.0;
    		SmartDashboard.putBoolean("Gear in Sight", true);
    	} else {
			SmartDashboard.putBoolean("Gear In Sight", false);
			//TODO: test this value and direction
    	}
    	*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return centerX.length == 2;
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
