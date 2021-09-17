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

	public static final int CONVEYOR_A = 9; //TODO: fix talon numbers
	public static final int FEEDER = 8;
	public static final int SHOOTER_LOW_A = 10;
	//public static final int SHOOTER_LOW_B = 11;
	public static final int SHOOTER_HIGH_A = 7;
	//public static final int SHOOTER_HIGH_B = 13;
	
	// Solenoids for Shifters
	public static final int SHIFTER_RIGHT_A = 0;
	public static final int SHIFTER_RIGHT_B = 1;
	public static final int SHIFTER_LEFT_A = 2;
	public static final int SHIFTER_LEFT_B = 3;

	// Encoder-to-distance constants
	public static final double CODES_PER_WHEEL_REV = 256.0 * (60.0 / 24.0)
			* (36.0 / 12.0);
	// 256.0 is the number of ticks per revolution on the encoder
	// (*4 = 1024 "native units" per rev)
	// 60/24 is the gearbox final stage output
	// 36/12 is the ratio of the stage that spins the encoder
	public static final double WHEEL_DIAMETER = 4.0; // inches

}