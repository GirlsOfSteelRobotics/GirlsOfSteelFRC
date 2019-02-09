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
	
	public static final int DRIVE_LEFT_MASTER_TALON = 1; 
	public static final int DRIVE_LEFT_FOLLOWER_TALON = 2; 
	
	public static final int DRIVE_RIGHT_MASTER_TALON = 3; 
	public static final int DRIVE_RIGHT_FOLLOWER_TALON = 4;
	
	public static final int COLLECT_LEFT_TALON = 7;
	public static final int COLLECT_RIGHT_TALON = 6;

	public static final int PIVOT_TALON = 8;

	public static final int BABY_DRIVE_TALON = 9;
	
	public static final int CLIMBER_FRONT_TALON = 5;
	public static final int CLIMBER_BACK_TALON = 10;

	public static final int HATCH_RELAY = 0; 

	public static final int LIGHT_SENSOR_DIO = 0;
	public static final int LIDAR_LITE_DIO = 1;

	public static final int BLINKIN_LEFT_PWM = 0;
	public static final int BLINKIN_RIGHT_PWM = 1;

	public static final int VISION_CAMERA = 0; 


	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
