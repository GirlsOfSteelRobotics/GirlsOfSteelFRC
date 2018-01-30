/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int DRIVE_RIGHT_A = 1;
	public static final int DRIVE_RIGHT_B = 2;
	public static final int DRIVE_RIGHT_C = 3;
	public static final int DRIVE_LEFT_A = 4;
	public static final int DRIVE_LEFT_B = 5;
	public static final int DRIVE_LEFT_C = 6;
	
	public static final int LIDAR = 9;
	
	public static final int LIFT_A = 7;
	public static final int LIFT_B = 8;
	
	public static final int PIVOT = 13;
	
	public static final int COLLECT_RIGHT_A = 9;
	public static final int COLLECT_RIGHT_B = 10;
	public static final int COLLECT_LEFT_A = 11;
	public static final int COLLECT_LEFT_B = 12;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	// Solenoids for Shifters
	public static final int SHIFTER_RIGHT_A = 0;
	public static final int SHIFTER_RIGHT_B = 1;
	public static final int SHIFTER_LEFT_A = 2;
	public static final int SHIFTER_LEFT_B = 3;
	
	//Limit switch
	public static final int LIMIT_SWITCH = 1; //TODO
	
	public static final double CODES_PER_WHEEL_REV = 256.0 * (60.0 / 24.0)
			* (36.0 / 12.0) * 4;
	// 256.0 is the number of ticks per revolution on the encoder
	// (*4 = 1024 "native units" per rev)
	// 60/24 is the gearbox final stage output
	// 36/12 is the ratio of the stage that spins the encoder
	// 4 JUST BECAUSE
	
	public static final double WHEEL_DIAMETER = 6.0; // inches
	
}
