/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	    
	  	public static final int DRIVE_RIGHT_FRONT = 1;
		public static final int DRIVE_RIGHT_MIDDLE = 2;
		public static final int DRIVE_RIGHT_BACK = 3;
		public static final int DRIVE_LEFT_FRONT = 4;
		public static final int DRIVE_LEFT_MIDDLE = 5;
		public static final int DRIVE_LEFT_BACK = 6;
		
		public static final int SHOOT_1 = 1;
		public static final int SHOOT_2 = 2;
		public static final int SHOOT_3 = 3;
		
	    //you can add more things here. like more motors, encoders, things that need an object
	    //limit switches, shifters, etc
	    //public static DoubleSolenoid shifterRight; //example
	    //public static DoubleSolenoid shifterLeft;  //example
		
	    	//when labeling motors, 0 is never used, makes it easier
	    	/*
	    	 * talons are positioned like this
	    	 * ||-----||
	    	 *  |1---4|
	    	 * ||2---5||
	    	 *  |3---6|
	    	 * ||-----||
	    	 * sides may vary like is 1 is actually left (could be right side)
	    	 */
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
