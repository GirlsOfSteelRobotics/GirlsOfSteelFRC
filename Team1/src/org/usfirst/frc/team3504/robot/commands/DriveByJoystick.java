

package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3504.robot.Robot;

/**
 *
 */
public class DriveByJoystick extends Command {

    public DriveByJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSystem); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("Drive by Joystick", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSystem.takeJoystickInputs(Robot.oi.getDriveStick());
		
		SmartDashboard.putNumber("Drive Left Encoder ", Robot.driveSystem.getEncoderLeft());
		SmartDashboard.putNumber("Drive Right Encoder ", Robot.driveSystem.getEncoderRight());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}