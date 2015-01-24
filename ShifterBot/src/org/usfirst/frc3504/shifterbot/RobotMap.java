package org.usfirst.frc3504.shifterbot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import java.util.Vector;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static CANTalon driveSystemDriveLeft0;
	public static CANTalon driveSystemDriveRight0;
	public static CANTalon driveSystemDriveLeft1;
	public static CANTalon driveSystemDriveRight1;
	public static CANTalon driveSystemDriveLeft2;
	public static CANTalon driveSystemDriveRight2;
	public static RobotDrive driveSystemRobotDrive2;
	public static SpeedController accessoryMotorsAccessoryLeft;
	public static SpeedController accessoryMotorsAccessoryRight;
	public static DoubleSolenoid shiftersShifterLeft;
	public static DoubleSolenoid shiftersShifterRight;
	
	public static final int DRIVE_LEFT_ENCODER_A = 0;
	public static final int DRIVE_LEFT_ENCODER_B = 1;
	public static final int DRIVE_RIGHT_ENCODER_A = 2;
	public static final int DRIVE_RIGHT_ENCODER_B = 3;

 static void init() {
		driveSystemDriveLeft0 = new CANTalon(0);
		// LiveWindow.addActuator("Drive System", "Drive Left0",
		// driveSystemDriveLeft0);

		driveSystemDriveRight0 = new CANTalon(3);
		// LiveWindow.addActuator("Drive System", "Drive Right0",
		// driveSystemDriveRight0);

		driveSystemDriveLeft1 = new CANTalon(1);
		// LiveWindow.addActuator("Drive System", "Drive Left1",
		// driveSystemDriveLeft1);

		driveSystemDriveRight1 = new CANTalon(4);
		// LiveWindow.addActuator("Drive System", "Drive Right1",
		// driveSystemDriveRight1);

		driveSystemDriveLeft2 = new CANTalon(2);
		// LiveWindow.addActuator("Drive System", "Drive Left2",
		// driveSystemDriveLeft2);

		driveSystemDriveRight2 = new CANTalon(5);
		// LiveWindow.addActuator("Drive System", "Drive Right2",
		// driveSystemDriveRight2);

		// Follower: The m_motor will run at the same throttle as the specified
		// other talon.
		driveSystemDriveLeft1.changeControlMode(CANTalon.ControlMode.Follower);
		driveSystemDriveLeft2.changeControlMode(CANTalon.ControlMode.Follower);
		driveSystemDriveRight1.changeControlMode(CANTalon.ControlMode.Follower);
		driveSystemDriveRight2.changeControlMode(CANTalon.ControlMode.Follower);
		driveSystemDriveLeft1.set(0);
		driveSystemDriveRight1.set(3);
		driveSystemDriveLeft2.set(0);
		driveSystemDriveRight2.set(3);
	    
		driveSystemRobotDrive2 = new RobotDrive(driveSystemDriveLeft0, driveSystemDriveRight0);

		driveSystemRobotDrive2.setSafetyEnabled(true);
		driveSystemRobotDrive2.setExpiration(0.1);
		driveSystemRobotDrive2.setSensitivity(0.5);
		driveSystemRobotDrive2.setMaxOutput(1.0);
		//driveSystemRobotDrive2.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);        

		accessoryMotorsAccessoryLeft = new Victor(2);
		LiveWindow.addActuator("Accessory Motors", "Accessory Left", (Victor) accessoryMotorsAccessoryLeft);

		accessoryMotorsAccessoryRight = new Victor(3);
		LiveWindow.addActuator("Accessory Motors", "Accessory Right", (Victor) accessoryMotorsAccessoryRight);

		shiftersShifterLeft = new DoubleSolenoid(0, 1);
		shiftersShifterRight = new DoubleSolenoid(2, 3);  
		

	}
}
