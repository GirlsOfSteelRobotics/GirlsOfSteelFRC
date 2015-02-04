package org.usfirst.frc.team3504.robot.commands;
import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestPhotoSensor extends Command {

	private DigitalInput psensor_lightinput;
	private DigitalInput psensor_darkinput;
	
	
    public TestPhotoSensor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.chassis);
    	psensor_lightinput = new DigitalInput(RobotMap.PHOTOSENSOR_CHANNEL_LIGHTINPUT);
    	psensor_darkinput = new DigitalInput(RobotMap.PHOTOSENSOR_CHANNEL_DARKINPUT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("photosensorlight", psensor_lightinput.get());
    	SmartDashboard.putBoolean("photosensordark", psensor_darkinput.get());
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
