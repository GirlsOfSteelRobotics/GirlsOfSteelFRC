package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionMagic extends Command {
	
	private double encoderTicks; //in sensor units

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    public DriveByMotionMagic(double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	double rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
		encoderTicks = RobotMap.CODES_PER_WHEEL_REV * rotations;
		
		requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftTalon.setSelectedSensorPosition(0, 0, 20);
    	rightTalon.setSelectedSensorPosition(0, 0, 20);
    	
    	
    	System.out.println("DriveByMotionMagic encoder ticks: " + encoderTicks);
    	Robot.chassis.setVelocityPIDSlot();
    	System.out.println("DriveByMotionMagic initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftTalon.set(ControlMode.MotionMagic, encoderTicks);
		rightTalon.set(ControlMode.MotionMagic, -encoderTicks);
		System.out.println("DriveByMotionMagic execute");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
