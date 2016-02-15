package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FlapUp extends Command {

	private boolean rocker; //driveteam wants 4 buttons to control flap - while held up/down, when pressed all the way up/down
	
    public FlapUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.flap);
    	//rocker = isRocker;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.flap.setTalon(1);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	/*if (rocker == true) 
    		return false;
    	else */
    		return Robot.flap.getTopLimitSwitch() == true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.flap.setTalon(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
