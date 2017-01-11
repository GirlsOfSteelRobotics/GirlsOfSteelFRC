package org.usfirst.frc.team3504.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int DRIVE_LEFT_A = 1;
	public static final int DRIVE_LEFT_B = 2;
	public static final int DRIVE_LEFT_C = 3;
	public static final int DRIVE_RIGHT_A = 4; 
	public static final int DRIVE_RIGHT_B = 5; 
	public static final int DRIVE_RIGHT_C = 6;
	
	public static final int CLIMB_MOTOR_A = 7;
	public static final int CLIMB_MOTOR_B = 8;
	public static final int SHOOTER_MOTOR = 9;
	public static final int FLAP_MOTOR = 10;
	
	//Solenoids for Gears
	public static final int GEAR_PISTON_A = 20; //TO DO: fix
	public static final int GEAR_PISTON_B = 21; //TO DO: fix
}
