package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

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
	public static CANTalon driveSystemDriveLeft3;
	public static CANTalon driveSystemDriveRight3;
	
	public static RobotDrive driveRobotDrive;
	public static RobotManipulator manipulatorRobotManipulator;
	
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
