package org.usfirst.frc3504.shifterbot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static SpeedController driveSystemDriveLeft;
	public static SpeedController driveSystemDriveRight;
	public static RobotDrive driveSystemRobotDrive2;
	public static SpeedController accessoryMotorsAccessoryLeft;
	public static SpeedController accessoryMotorsAccessoryRight;
	public static DoubleSolenoid shiftersShifterLeft;
	public static DoubleSolenoid shiftersShifterRight;

	public static void init() {
		driveSystemDriveLeft = new Talon(0);
		LiveWindow.addActuator("Drive System", "Drive Left", (Talon) driveSystemDriveLeft);

		driveSystemDriveRight = new Talon(1);
		LiveWindow.addActuator("Drive System", "Drive Right", (Talon) driveSystemDriveRight);

		driveSystemRobotDrive2 = new RobotDrive(driveSystemDriveLeft, driveSystemDriveRight);

		driveSystemRobotDrive2.setSafetyEnabled(true);
		driveSystemRobotDrive2.setExpiration(0.1);
		driveSystemRobotDrive2.setSensitivity(0.5);
		driveSystemRobotDrive2.setMaxOutput(1.0);
		driveSystemRobotDrive2.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);        

		accessoryMotorsAccessoryLeft = new Victor(2);
		LiveWindow.addActuator("Accessory Motors", "Accessory Left", (Victor) accessoryMotorsAccessoryLeft);

		accessoryMotorsAccessoryRight = new Victor(3);
		LiveWindow.addActuator("Accessory Motors", "Accessory Right", (Victor) accessoryMotorsAccessoryRight);

		shiftersShifterLeft = new DoubleSolenoid(1, 0, 1);
		shiftersShifterRight = new DoubleSolenoid(1, 2, 3);      
	}
}
