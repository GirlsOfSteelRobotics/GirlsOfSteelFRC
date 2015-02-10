package org.usfirst.frc.team3504.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Joystick ports -> should align with driver station
	public static final int OPERATOR_JOYSTICK = 1;
	public static final int CHASSIS_JOYSTICK = 0;

	// Drive ports
	public static final int FRONT_LEFT_WHEEL_CHANNEL = 0; //Motors
	public static final int REAR_LEFT_WHEEL_CHANNEL = 1;
	public static final int FRONT_RIGHT_WHEEL_CHANNEL = 2;
	public static final int REAR_RIGHT_WHEEL_CHANNEL = 3;
	public static final int GYRO_PORT = 0; //Gyro
	public static final int ULTRASONICSENSOR_CHANNEL = 3; //Ultrasonic
	public static final int PHOTOSENSOR_CHANNEL_LIGHTINPUT = 1; //Photosensor
	public static final int PHOTOSENSOR_CHANNEL_DARKINPUT = 2;

	//Shack ports
	public static final int SHACK_TALON = 5; //Motor 
	public static final int SHACK_LEFT_LIMIT = 6; //Limit Switches
	public static final int SHACK_RIGHT_LIMIT = 7; 
	
	
	/* shaft port = 5
	 * //Forklift ports
	 * public static final int FORKLIFT_CHANNEL = 17; //Motors
	 * 
	 * //Finger ports
	 * public static final int RIGHT_FINGER_PISTON = 5; //Pistons
	 * public static final int LEFT_FINGER_PISTON = 6;
	 * public static final int RIGHT_FINGER_ENCODER_A = 10; //Encoders
	 * public static final int RIGHT_FINGER_ENCODER_B = 11;
	 * public static final int LEFT_FINGER_ENCODER_A = 12;
	 * public static final int LEFT_FINGER_ENCODER_B = 13;
	 * public static final int RIGHT_FINGER_LIMIT = 14; //Limit Switches
	 * public static final int LEFT_FINGER_LIMIT = 15;
	 * 
	 * 
	 * public static final int LEFT_WEDGE_CHANNEL = 7; //Motors
	 * public static final int RIGHT_WEDGE_CHANNEL = 8;
	 * public static final int LEFT_WEDGE_LIMIT = 16; //Limit Switches
	 * public static final int RIGHT_WEDGE_LIMIT = 17;
	 * public static final int TRIANGLE_PEG_SOLENOID = 24;
	 * 
	 * //Door ports
	 * public static final int LEFT_DOOR_CHANNEL = 20; //Motors
	 * public static final int RIGHT_DOOR_CHANNEL = 21;
	 */

	//Collector ports
	public static final int LEFT_COLLECTOR_WHEEL = 10; //Motors
	public static final int RIGHT_COLLECTOR_WHEEL = 19;
	public static final int RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL = 0; //Pistons
	public static final int RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL = 1;
	public static final int LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL = 2;
	public static final int LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL = 3;
	public static final int LEFT_COLLECTOR_LIMIT = 18; //Limit Switches
	public static final int RIGHT_COLLECTOR_LIMIT = 19;
}
