package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveByJoystick;
import frc.robot.subsystems.*;

public class Chassis extends Subsystem {

	private WPI_TalonSRX masterLeft;
	private WPI_TalonSRX followerLeft;

	private WPI_TalonSRX masterRight;
	private WPI_TalonSRX followerRight;
	
	private DifferentialDrive drive;

	public Chassis () {
		masterLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER_TALON);
		followerLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_TALON);

		masterRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER_TALON); 
		followerRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_TALON); 
		
		masterLeft.setNeutralMode(NeutralMode.Brake);
		followerLeft.setNeutralMode(NeutralMode.Brake);

		masterRight.setNeutralMode(NeutralMode.Brake);
		followerRight.setNeutralMode(NeutralMode.Brake);

		// inverted should be true for Laika
		masterLeft.setInverted(false);
		followerLeft.setInverted(false);

		masterRight.setInverted(false);
		followerRight.setInverted(false);
		
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
    
    public void driveByJoystick(double yDir, double xDir) {
		SmartDashboard.putString("driveByJoystick?", yDir + "," + xDir); 
		double forward = yDir*Math.abs(yDir);
    	drive.arcadeDrive(forward, xDir);
	}
	
	public void setSpeed(double speed){
		drive.arcadeDrive(speed, 0);
	}

    public void stop() {
    	drive.stopMotor(); 
	}
}

