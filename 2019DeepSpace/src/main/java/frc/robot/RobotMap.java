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
	
	// Talon SRX ID numbers	assigned via the Phoenix Tuning utility
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

	public static final int CLIMBER_FRONT_FOLLOWER_TALON = 11;
	public static final int CLIMBER_BACK_FOLLOWER_TALON = 12;

	public static final int HATCH_TALON = 13;

	// Digital Input/Output (DIO) Ports on the RoboRIO
	public static final int LIDAR_DIO = 0;

	// Pulse-width Modulation (PWM) Ports on the RoboRIO
	public static final int BLINKIN_LEFT_PWM = 0;
	public static final int BLINKIN_LEFT2_PWN = 1;
	public static final int BLINKIN_RIGHT_PWM = 2;

	// Camera numbers assigned by the USB driver 
	// based on where they're plugged in
	public static final int DRIVER_CAMERA = 0;
	public static final int VISION_CAMERA = 1;
}