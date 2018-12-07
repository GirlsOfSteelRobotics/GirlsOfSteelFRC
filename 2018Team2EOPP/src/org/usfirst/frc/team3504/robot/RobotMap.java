/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
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
	
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int JOYSTICK_1=0;
	public static final int JOYSTICK_2=1;
	
	
	public static final int LEFT_MASTER_PORT=1;
	public static final int LEFT_SLAVE_A_PORT=2;
	public static final int LEFT_SLAVE_B_PORT=3;
	
	public static final int RIGHT_MASTER_PORT=4;
	public static final int RIGHT_SLAVE_A_PORT=5;
	public static final int RIGHT_SLAVE_B_PORT=6;
	
	public static final int SHOOTER_1_MASTER=7;
	public static final int SHOOTER_1_FOLLOWER=8;
	
	public static final int SHOOTER_2_MASTER=9;
}
