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

	private double codes; //in sensor units

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

	private double leftInitial;
	private double rightInitial;
	
	private Shifters.Speed speed;

	public DriveByDistance(double inches, Shifters.Speed speed) {
		double rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
		codes = RobotMap.CODES_PER_WHEEL_REV * rotations;
		this.speed = speed;

		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.chassis.setInverted(true);
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

		System.out.println("Drive by Distance Started " + codes);

		leftInitial = leftTalon.getSelectedSensorPosition(0);
		rightInitial = rightTalon.getSelectedSensorPosition(0);

		leftTalon.set(ControlMode.Position, (codes + leftInitial));
		rightTalon.set(ControlMode.Position, (codes + rightInitial));

		System.out.println("LeftInitial: " + leftInitial + " RightInitial: " + rightInitial);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//System.out.println("LeftInitial " + leftInitial);
		//System.out.println("RightInitial " + rightInitial);
		//System.out.println("Rotations: " + rotations);
		//System.out.println("Left goal: " + (rotations + leftInitial));
		//System.out.println("Right goal: " + (rotations + rightInitial));

		leftTalon.set(ControlMode.Position, (codes + leftInitial));
		rightTalon.set(ControlMode.Position, (codes + rightInitial));

		SmartDashboard.putNumber("Drive Talon Left Goal", codes);
		SmartDashboard.putNumber("Drive Talon Left Position", leftTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getClosedLoopError(0));

		System.out.println("Left Goal: " + ((codes + leftInitial)) + " Right Goal: " + (codes + rightInitial));
		System.out.println("Left Position: " + leftTalon.getSelectedSensorPosition(0) + " Right Position: " + rightTalon.getSelectedSensorPosition(0));
		System.out.println("Left Error: " + ((codes + leftInitial) - leftTalon.getSelectedSensorPosition(0)));
		System.out.println("Right Error: " + ((codes + rightInitial) - rightTalon.getSelectedSensorPosition(0)));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
		if (codes > 0) {
			if ((rightTalon.getSelectedSensorPosition(0) > (codes + rightInitial))
					&& (leftTalon.getSelectedSensorPosition(0) > (codes + leftInitial)))
			{
				System.out.println("Finish Case #1");
				return true;
			}
			else return false;
		} else if (codes < 0) {
			if ((rightTalon.getSelectedSensorPosition(0) < (codes + rightInitial))
					&& (leftTalon.getSelectedSensorPosition(0) < (codes + leftInitial)))
			{
				System.out.println("Finish Case #2");
				return true;
			}
			else return false;
		} else {
			System.out.println("Finish Case #3");
			return true;
		}
		
		//return false;
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
