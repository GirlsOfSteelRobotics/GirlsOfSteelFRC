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
	public static final int AUTO_JOYSTICK = 2;

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
	//public static final int SHACK_TALON = 5; //Motor 
	public static final int LEFT_SHACK_CHANNEL_A = 4; //In
	public static final int LEFT_SHACK_CHANNEL_B = 5; //Out
	public static final int LEFT_SHACK_MODULE = 1;
	public static final int RIGHT_SHACK_CHANNEL_A = 6; //In
	public static final int RIGHT_SHACK_CHANNEL_B = 7; //Out
	public static final int RIGHT_SHACK_MODULE = 1;
	
	
	//PCM Module Ports
	public static final int PCM_MODULE_0 = 0;
	public static final int PCM_MODULE_1 = 1;
	
	//Forklift ports
	public static final int FORKLIFT_CHANNEL_A = 17; //Motors
	//public static final int FORKLIFT_CHANNEL_B = ;
	public static final int FORKLIFT_TOP_LIMIT = 0;
	public static final int FORKLIFT_BOTTOM_LIMIT = 1;
	
	//Finger ports
	public static final int RIGHT_FINGER_PISTON_A = 6; //Pistons
	public static final int RIGHT_FINGER_PISTON_B = 7;
	public static final int RIGHT_FINGER_MODULE = 0;
	public static final int LEFT_FINGER_PISTON_A = 3;
	public static final int LEFT_FINGER_PISTON_B = 2;
	public static final int LEFT_FINGER_MODULE = 1;
	
	//Door ports
	public static final int LEFT_DOOR_CHANNEL_A = 1;
	public static final int LEFT_DOOR_CHANNEL_B = 0;
	public static final int LEFT_DOOR_MODULE = 1;
	public static final int RIGHT_DOOR_CHANNEL_A = 4;
	public static final int RIGHT_DOOR_CHANNEL_B = 5;
	public static final int RIGHT_DOOR_MODULE = 0;

	//Collector ports
	public static final int LEFT_COLLECTOR_WHEEL = 10; //Motors
	public static final int RIGHT_COLLECTOR_WHEEL = 19;
	public static final int RIGHT_COLLECTOR_SOLENOID_FORWARDCHANNEL = 1; //Pistons
	public static final int RIGHT_COLLECTOR_SOLENOID_REVERSECHANNEL = 0;
	public static final int RIGHT_COLLECTOR_MODULE = 0;
	public static final int LEFT_COLLECTOR_SOLENOID_FORWARDCHANNEL = 3;
	public static final int LEFT_COLLECTOR_SOLENOID_REVERSECHANNEL = 2;
	public static final int LEFT_COLLECTOR_MODULE = 0;
}
