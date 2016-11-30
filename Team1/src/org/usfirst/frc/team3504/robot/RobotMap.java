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
	
	// Each side of the drive system has three motors,
	// all driven at the same speed
	public static final int DRIVE_LEFT_A_CAN_ID = 4; 
	public static final int DRIVE_LEFT_B_CAN_ID = 5;
	public static final int DRIVE_LEFT_C_CAN_ID = 6;

	public static final int DRIVE_RIGHT_A_CAN_ID = 1;
	public static final int DRIVE_RIGHT_B_CAN_ID = 2;
	public static final int DRIVE_RIGHT_C_CAN_ID = 3;
	public static final int ARM_PIVOT_CAN_ID = 15;
	public static final int COLLECTOR_CAN_ID = 14;
	
	// The output shaft of the drive system has a quadrature encoder
	public static final int DRIVE_LEFT_ENCODER_A = 0;
	public static final int DRIVE_LEFT_ENCODER_B = 1;
	public static final int DRIVE_RIGHT_ENCODER_A = 2;
	public static final int DRIVE_RIGHT_ENCODER_B = 3;
	
	
	// Encoder-to-distance constants
	// How many ticks are there on the encoder wheel?
	private static final double pulsePerRevolution = 360;
	// How far to we travel when the encoder turns one full revolution?
	// Gear ratio is turns of the wheel per turns of the encoder
	//FIXME - gear ratio is just a swag
	private static final double distancePerRevolution = 8.0/*wheel size*/ * Math.PI * (16/16)/*gear ratio*/;
    // Given our set of wheels and gear box, how many inches do we travel per pulse?
	public static final double DISTANCE_PER_PULSE = distancePerRevolution / pulsePerRevolution;
}
