package org.usfirst.frc.team3504.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int DRIVE_LEFT_A = 4;
	public static final int DRIVE_LEFT_B = 5;
	public static final int DRIVE_LEFT_C = 6;
	public static final int DRIVE_RIGHT_A = 1; 
	public static final int DRIVE_RIGHT_B = 2; 
	public static final int DRIVE_RIGHT_C = 3;
	
	public static final int CLIMB_MOTOR = 7;
	public static final int SHOOTER_MOTOR_A = 8; //TODO: fix
	public static final int SHOOTER_MOTOR_B = 9;
	
	//Solenoids for Shifters
	public static final int SHIFTER_LEFT_A = 2; 
	public static final int SHIFTER_LEFT_B = 3; 
	public static final int SHIFTER_RIGHT_A = 0; 
	public static final int SHIFTER_RIGHT_B = 1; 
	
	//Solenoids for Gear Cover
	public static final int GEAR_COVER = 13; //TODO: fix
	
	//camera numbers
	public static final int CAMERA_GEAR = 0;
	public static final int CAMERA_CLIMB = 1;
	
	// Encoder-to-distance constants
	// How many ticks are there on the encoder wheel?
	private static final double pulsePerRevolution = 360;
	// How far to we travel when the encoder turns one full revolution?
	// Gear ratio is turns of the wheel per turns of the encoder
	private static final double distancePerRevolutionHighGear = 4.0/*wheel size*/ * Math.PI * (1/7.08)/*gear ratio*/; //(9.07)
	private static final double distancePerRevolutionLowGear = 4.0/*wheel size*/ * Math.PI * (1/26.04)/*gear ratio*/; //28.33/15.9 (33.33) 
    // Given our set of wheels and gear box, how many inches do we travel per pulse?
	public static final double DISTANCE_PER_PULSE_HIGH_GEAR = distancePerRevolutionHighGear / pulsePerRevolution;
	public static final double DISTANCE_PER_PULSE_LOW_GEAR = distancePerRevolutionLowGear / pulsePerRevolution;
}

