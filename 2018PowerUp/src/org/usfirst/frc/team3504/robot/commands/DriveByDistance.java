package org.usfirst.frc.team3504.robot.commands;


import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {

	private double rotations;

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

	private double leftInitial;
	private double rightInitial;
	
	private Shifters.Speed speed;

	public DriveByDistance(double inches, Shifters.Speed speed) {
		rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
		rotations = 4096 * rotations;
		this.speed = speed;

		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shifters.shiftGear(speed);
		Robot.chassis.setFollowerMode();

		// Robot.chassis.setupFPID(leftTalon);
		// Robot.chassis.setupFPID(rightTalon);
		
		if (speed == Shifters.Speed.kLow){
			leftTalon.config_kF(0, 0, 0);
			leftTalon.config_kP(0, 0.3, 0);
			leftTalon.config_kI(0, 0, 0);
			leftTalon.config_kD(0, 0, 0);
			
			rightTalon.config_kF(0, 0, 0);
			rightTalon.config_kP(0, 0.3, 0);
			rightTalon.config_kI(0, 0, 0);
			rightTalon.config_kD(0, 0, 0);
		}
		else if (speed == Shifters.Speed.kHigh){
			leftTalon.config_kF(0, 0, 0);
			leftTalon.config_kP(0, 0.02, 0);
			leftTalon.config_kI(0, 0, 0);
			leftTalon.config_kD(0, 0.04, 0);
			
			rightTalon.config_kF(0, 0, 0);
			rightTalon.config_kP(0, 0.02, 0);
			rightTalon.config_kI(0, 0, 0);
			rightTalon.config_kD(0, 0.04, 0);
		}
		

		// leftTalon.setPosition(0.0);
		// rightTalon.setPosition(0.0);

		System.out.println("Drive by Distance Started " + rotations);

		leftInitial = leftTalon.getSelectedSensorPosition(0);
		rightInitial = rightTalon.getSelectedSensorPosition(0);

		leftTalon.set(ControlMode.Position, (rotations + leftInitial));
		rightTalon.set(ControlMode.Position, rotations + rightInitial);

		System.out.println("LeftInitial: " + leftInitial + " RightInitial: " + rightInitial);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("LeftInitial " + leftInitial);
		System.out.println("RightInitial " + rightInitial);
		System.out.println("Rotations: " + rotations);
		System.out.println("Left goal: " + (rotations + leftInitial));
		System.out.println("Right goal: " + (rotations + rightInitial));

		leftTalon.set(ControlMode.Position, (rotations + leftInitial));
		rightTalon.set(ControlMode.Position, (rotations + rightInitial));

		SmartDashboard.putNumber("Drive Talon Left Goal", rotations);
		SmartDashboard.putNumber("Drive Talon Left Position", leftTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getClosedLoopError(0));

		System.out.println("Left Goal " + ((rotations + leftInitial)) + " Right Goal " + (rotations + rightInitial));
		System.out.println("Left Position " + leftTalon.getSelectedSensorPosition(0) + " Right Position " + rightTalon.getSelectedSensorPosition(0));
		System.out.println("Left Error " + ((rotations + leftInitial) - leftTalon.getSelectedSensorPosition(0)));
		System.out.println("Right Error " + (((rotations + rightInitial)) - rightTalon.getSelectedSensorPosition(0)));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (rotations > 0) {
			//System.out.println("Finish Case #1");
			return ((rightTalon.getSelectedSensorPosition(0) > rotations + rightInitial)
					&& (leftTalon.getSelectedSensorPosition(0) > rotations + leftInitial));
		} else if (rotations < 0) {
			//System.out.println("Finish Case #2");
			return ((rightTalon.getSelectedSensorPosition(0) < rotations + rightInitial)
					&& (leftTalon.getSelectedSensorPosition(0) < rotations + leftInitial));
		} else {
			//System.out.println("Finish Case #3");
			return true;
		}
		
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shifters.shiftGear(Shifters.Speed.kLow);
		System.out.println("DriveByDistance Finished");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
