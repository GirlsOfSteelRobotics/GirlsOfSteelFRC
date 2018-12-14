package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveByJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Chassis extends Subsystem {
	
	private final WPI_TalonSRX talonSRX1 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FRONT);
	private final WPI_TalonSRX talonSRX2 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MIDDLE);
	private final WPI_TalonSRX talonSRX3 = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_BACK);
	private final WPI_TalonSRX talonSRX4 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FRONT);
	private final WPI_TalonSRX talonSRX5 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MIDDLE);
	private final WPI_TalonSRX talonSRX6 = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_BACK);
	

	private DifferentialDrive drive;

	public Chassis() {
		// This is the constructor
		
		

		// Safety and brakes ----------------------------------
		talonSRX1.setNeutralMode(NeutralMode.Brake);
		talonSRX4.setNeutralMode(NeutralMode.Brake);
		

		talonSRX2.set(ControlMode.Follower, talonSRX1.getDeviceID());
		talonSRX3.set(ControlMode.Follower, talonSRX1.getDeviceID());
		talonSRX5.set(ControlMode.Follower, talonSRX2.getDeviceID());
		talonSRX6.set(ControlMode.Follower, talonSRX2.getDeviceID());
		

	    	talonSRX1.setSafetyEnabled(false);
	    	talonSRX2.setSafetyEnabled(false);
	    	talonSRX3.setSafetyEnabled(false);
	    	
	    	talonSRX4.setSafetyEnabled(false);
	    	talonSRX5.setSafetyEnabled(false);
	    	talonSRX6.setSafetyEnabled(false);
		// -----------------------------------------------------
		

		drive = new DifferentialDrive(talonSRX1, talonSRX4);
		drive.setSafetyEnabled(true);
		drive.setExpiration(0.1);
		drive.setMaxOutput(1.0);

		// reverse();

		setFollowerMode();

		// invert(true); //uncomment if needed
		// if needed to invert the talons, do before putting into the drive
		drive = new DifferentialDrive(talonSRX4, talonSRX1);

		drive.setSafetyEnabled(false);
		// this line is to set it so the joystick isn't too sensitive to input
		drive.setDeadband(0.02);
	}

	public void driveByJoystick(double yDir, double xDir) {
		SmartDashboard.putString("driveByJoystick?", yDir + "," + xDir);
		drive.arcadeDrive(yDir, xDir);
	}

	public void driveForward() {
		talonSRX4.set(0.4);
		talonSRX1.set(0.4);
	}

	@Override
	public void initDefaultCommand() {
		//setDefaultCommand(new DriveByJoystick());

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	@Override
	public void periodic() {
		// if you had something that needed to executed on a periodic sort of time
		// instead of tiem, this is where you would put the commands
	}

	public void driveByTank(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void driveByArcade(double mag, double rotate) {
		// speed and how tight the rotation is [-1,1]
		drive.arcadeDrive(mag, rotate);
	}

	public void invert(boolean x) {
		// if the moters are spinning in opposite directions
		talonSRX1.setInverted(false);
		talonSRX2.setInverted(false);
		talonSRX3.setInverted(false);

		talonSRX4.setInverted(x);
		talonSRX5.setInverted(x);
		talonSRX6.setInverted(x);
	}

	public void reverse() {
		// if the motors are together but completely reversed (do not pair with invert)
		talonSRX1.setInverted(true);
		talonSRX3.setInverted(true);
		talonSRX5.setInverted(true);

		talonSRX2.setInverted(true);
		talonSRX4.setInverted(true);
		talonSRX6.setInverted(true);
	}

	public void setFollowerMode() {
		// follower code
		talonSRX2.follow(talonSRX1, FollowerType.PercentOutput);
		talonSRX3.follow(talonSRX1, FollowerType.PercentOutput);

		talonSRX5.follow(talonSRX4, FollowerType.PercentOutput);
		talonSRX6.follow(talonSRX4, FollowerType.PercentOutput);
	}

	public void stop() {
		drive.stopMotor();
	}
}
