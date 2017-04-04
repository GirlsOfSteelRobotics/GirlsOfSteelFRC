package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByJoystick extends Command {
	
	private CANTalon leftTalon = Robot.chassis.getLeftTalon();
	private CANTalon rightTalon = Robot.chassis.getRightTalon();
	
    public DriveByJoystick() {
    	// Use requires() here to declare subsystem dependencies
    	// eg. requires(chassis);
    	requires(Robot.chassis);	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Change mode to Percent Vbus
    	Robot.chassis.setPercentVbusMode();
    	
    	//V per sec; 12 = zero to full speed in 1 second
    	leftTalon.setVoltageRampRate(24.0);
    	rightTalon.setVoltageRampRate(24.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.arcadeDrive();
    	SmartDashboard.putNumber("drive by joystick Y?", Robot.oi.getDrivingJoystickY());
    	SmartDashboard.putNumber("drive by joystick X?", Robot.oi.getDrivingJoystickX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    public void stop(){
    	Robot.chassis.stop();
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
