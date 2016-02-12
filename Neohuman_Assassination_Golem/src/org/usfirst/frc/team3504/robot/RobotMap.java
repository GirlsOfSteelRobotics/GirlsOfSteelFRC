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
	
	//Talons for Driving
	public static final int DRIVE_LEFT_A = 4; //TODO: fix these numbers
	public static final int DRIVE_LEFT_B = 5;
	public static final int DRIVE_LEFT_C = 6;
	
	public static final int DRIVE_RIGHT_A = 1;
	public static final int DRIVE_RIGHT_B = 2;
	public static final int DRIVE_RIGHT_C = 3;
	

	//Talons for claw

	public static final int CLAW_MOTOR = 9;
	
	//Talons for pivot
	public static final int PIVOT_MOTOR = 8;
	
	//solenoids for shifters
	public static final int SHIFTER_LEFT_A = 0;
	public static final int SHIFTER_LEFT_B = 1;
	public static final int SHIFTER_RIGHT_A = 2;
	public static final int SHIFTER_RIGHT_B = 3;

	//talons for flap
	public static final int FLAP_MOTOR = 7; //TODO: fix these numbers

	// Encoder-to-distance constants
	// How many ticks are there on the encoder wheel?
	private static final double pulsePerRevolution = 360;
	// How far to we travel when the encoder turns one full revolution?
	// Gear ratio is turns of the wheel per turns of the encoder
	private static final double distancePerRevolutionHighGear = 8.0/*wheel size*/ * Math.PI * (1/27.21)/*gear ratio*/; //9.07
	private static final double distancePerRevolutionLowGear = 8.0/*wheel size*/ * Math.PI * (1/28.33)/*gear ratio*/; //33.33
    // Given our set of wheels and gear box, how many inches do we travel per pulse?
	public static final double DISTANCE_PER_PULSE_HIGH_GEAR = distancePerRevolutionHighGear / pulsePerRevolution;
	public static final double DISTANCE_PER_PULSE_LOW_GEAR = distancePerRevolutionLowGear / pulsePerRevolution;
}
