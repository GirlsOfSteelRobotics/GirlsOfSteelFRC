package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSpeedControl extends Command {
	
	private int time;
	
	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
	private final static int DRIVE_SPEED = 2000;

    public AutoSpeedControl() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.setInverted(true);
    	
    	time = 0;

		leftTalon.config_kF(0, 2*1023.0/6500.0, 0);
		leftTalon.config_kP(0, 0.1023, 0);
		leftTalon.config_kI(0, 0, 0);
		leftTalon.config_kD(0, 0, 0);
		
		rightTalon.config_kF(0, 2*1023.0/6500.0, 0);
		rightTalon.config_kP(0, 0.1023, 0);
		rightTalon.config_kI(0, 0, 0);
		rightTalon.config_kD(0, 0, 0);
		
    	leftTalon.set(ControlMode.Velocity, DRIVE_SPEED);
		rightTalon.set(ControlMode.Velocity, DRIVE_SPEED);
		
		System.out.println("AutoSpeedControl: Initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time++;
		leftTalon.set(ControlMode.Velocity, DRIVE_SPEED);
		rightTalon.set(ControlMode.Velocity, DRIVE_SPEED);
		System.out.println("AutoSpeedControl: time = " + time + " leftError = " + leftTalon.getClosedLoopError(0) + " rightError = " + rightTalon.getClosedLoopError(0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (time >= 400)
        {
        	System.out.println("AutoSpeedControl: Finished");
        	return true;
        }
        else return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
