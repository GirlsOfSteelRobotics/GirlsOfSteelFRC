package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {

	private final CANSparkMax masterLeft;
	private final CANSparkMax followerLeft;

	private final CANSparkMax masterRight;
	private final CANSparkMax followerRight;
	
	private final DifferentialDrive drive;

	public Chassis () {
		masterLeft = new CANSparkMax(Constants.DRIVE_LEFT_MASTER_SPARK, MotorType.kBrushless);
		followerLeft = new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, MotorType.kBrushless);

		masterRight = new CANSparkMax(Constants.DRIVE_RIGHT_MASTER_SPARK, MotorType.kBrushless); 
		followerRight = new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, MotorType.kBrushless); 
		
		masterLeft.setNeutralMode(NeutralMode.Brake);
		followerLeft.setNeutralMode(NeutralMode.Brake);

		masterRight.setNeutralMode(NeutralMode.Brake);
		followerRight.setNeutralMode(NeutralMode.Brake);

		// inverted should be true for Laika
		// masterLeft.setInverted(true);
		// followerLeft.setInverted(true);

		// masterRight.setInverted(true);
		// followerRight.setInverted(true);
		
		followerLeft.follow(masterLeft, false);
		followerRight.follow(masterRight, false);
		
		drive = new DifferentialDrive(masterLeft, masterRight);
		drive.setSafetyEnabled(true);
		drive.setExpiration(0.1);
		drive.setMaxOutput(0.8);
	}

	
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

	public CANSparkMax getLeftSparkMax(){
		return masterLeft;
	}

	public CANSparkMax getRightSparkMax(){
		return masterRight;
	}
    
    public void driveByJoystick(final double yDir, final double xDir) {
		SmartDashboard.putString("driveByJoystick?", yDir + "," + xDir); 
    	drive.arcadeDrive(ydir, xDir);
	}
	
	public void setSpeed(final double speed){
		drive.arcadeDrive(speed, 0);
	}

	public void setSpeedAndSteer(double speed, double steer){
		drive.arcadeDrive(speed, steer);
	}
	
    public void stop() {
    	drive.stopMotor(); 
	}
}