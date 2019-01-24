/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static final int JOYSTICK_PORT = 0;
	public static final int DRIVE_MASTER_PORT = 1;
	
	public static final int LEFT_MASTER_PORT = 1; 
	public static final int LEFT_DRIVE_A_PORT = 2; 
	public static final int LEFT_DRIVE_B_PORT = 3; 
	
	public static final int RIGHT_MASTER_PORT = 4; 
	public static final int RIGHT_DRIVE_A_PORT = 5;
	public static final int RIGHT_DRIVE_B_PORT = 6;
	
	public static final int COLLECT_LEFT = 8;
	public static final int COLLECT_RIGHT = 9;

	public static final int WRIST = 10;//TODO; ADJUST 

	public static final int PISTON_FRONT_A1 = 4;
	public static final int PISTON_FRONT_A2 = 5; 
	public static final int PISTON_FRONT_B1 = 6; 
	public static final int PISTON_FRONT_B2 = 7; 
	public static final int PISTON_BACK_A1 = 0; 
	public static final int PISTON_BACK_A2 = 1; 
	public static final int PISTON_BACK_B1 = 2; 
	public static final int PISTON_BACK_B2 = 3; 

	public static final int LIGHT_SENSOR_PORT = 0;


	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
