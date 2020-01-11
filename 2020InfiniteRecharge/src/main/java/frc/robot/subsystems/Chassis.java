package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.PWMSpeedController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.command.subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.commands.DriveByJoystick;
import frc.robot.subsystems.*;

public class Chassis extends Subsystem {

	private final CANSparkMax masterLeft;
	private final CANSparkMax followerLeft;

	private final CANSparkMax masterRight;
	private final CANSparkMax followerRight;
	
	private final DifferentialDrive drive;

	public Chassis () {
		masterLeft = new CANSparkMax(Constants.DRIVE_LEFT_MASTER_SPARK);
		followerLeft = new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK);

		masterRight = new CANSparkMax(Constants.DRIVE_RIGHT_MASTER_SPARK); 
		followerRight = new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK); 
		
		masterLeft.setNeutralMode(NeutralMode.Brake);
		followerLeft.setNeutralMode(NeutralMode.Brake);

		masterRight.setNeutralMode(NeutralMode.Brake);
		followerRight.setNeutralMode(NeutralMode.Brake);

		// inverted should be true for Laika
		// masterLeft.setInverted(true);
		// followerLeft.setInverted(true);

		// masterRight.setInverted(true);
		// followerRight.setInverted(true);
		
		followerLeft.follow(masterLeft, FollowerType.PercentOutput);
		followerRight.follow(masterRight, FollowerType.PercentOutput);
		
		drive = new DifferentialDrive(masterLeft, masterRight);
		drive.setSafetyEnabled(true);
		drive.setExpiration(0.1);
		drive.setMaxOutput(0.8);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveByJoystick()); 
	}
	
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

	public WPI_TalonSRX getLeftTalon(){
		return masterLeft;
	}

	public WPI_TalonSRX getRightTalon(){
		return masterRight;
	}
    
    public void driveByJoystick(final double yDir, final double xDir) {
		SmartDashboard.putString("driveByJoystick?", yDir + "," + xDir); 
		final double forward = yDir*Math.abs(yDir);
    	drive.arcadeDrive(forward, xDir);
	}
	
	public void setSpeed(final double speed){
		drive.arcadeDrive(speed, 0);
	}

    public void stop() {
    	drive.stopMotor(); 
	}
}