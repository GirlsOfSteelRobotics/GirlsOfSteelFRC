package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
  *parameter angle should be between -179.9 and 179.9 degrees
 * I'm assuming this method will only be called if we are actually using the gyro to turn...
 */
public class RotateToDesiredAngle extends Command {

	private double move;
	private double desiredAngle;
	
    public RotateToDesiredAngle(double moveValue, double angle) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.chassis);
    	move = moveValue;
    	desiredAngle = angle;
    }
   
    // Called just before this Command runs the first time
    protected void initialize() {
    //	Robot.chassis.turnController.setSetpoint(desiredAngle);
  //	Robot.chassis.turnController.enable();
    	//Robot.chassis.resetGyro(); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 try {
              // Use the joystick X axis for lateral movement,        
              // Y axis for forward movement, and the current           
              // calculated rotation rate (or joystick Z axis),         
              // depending upon whether "rotate to angle" is active.    
              Robot.chassis.drive(move, Robot.chassis.getRotationAngleRate());
          } catch( RuntimeException ex ) {
              DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
          }
    }

    //Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.chassis.getGyroAngle()) >= Math.abs(desiredAngle) ;
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
