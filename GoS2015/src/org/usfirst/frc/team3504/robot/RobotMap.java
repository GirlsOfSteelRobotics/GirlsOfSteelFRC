package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.CANTalon;

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

	// Joystick ports -> should align with driver station
	public static final int OPERATOR_JOYSTICK = 1;
	public static final int CHASSIS_JOYSTICK = 0;

	// Drive ports
	public static final int FRONT_LEFT_WHEEL_CHANNEL = 1; // Motors
	public static final int REAR_LEFT_WHEEL_CHANNEL = 2;
	public static final int FRONT_RIGHT_WHEEL_CHANNEL = 3;
	public static final int REAR_RIGHT_WHEEL_CHANNEL = 4;
	public static final int GYRO_PORT = 0; // Gyro
	public static final int FRONT_LEFT_WHEEL_ENCODER_A = 0; // Encoders
	public static final int FRONT_LEFT_WHEEL_ENCODER_B = 1;
	public static final int REAR_LEFT_WHEEL_ENCODER_A = 2;
	public static final int REAR_LEFT_WHEEL_ENCODER_B = 3;
	public static final int FRONT_RIGHT_WHEEL_ENCODER_A = 4;
	public static final int FRONT_RIGHT_WHEEL_ENCODER_B = 5;
	public static final int REAR_RIGHT_WHEEL_ENCODER_A = 6;
	public static final int REAR_RIGHT_WHEEL_ENCODER_B = 7;
	public static final int ULTRASONICSENSOR_CHANNEL = 8;

	/*
	 * //Forklift ports public static final int FORKLIFT_CHANNEL = 4; //Motors
	 * public static final int LEFT_FORKLIFT_LIMIT = 8; //Limit Switches public
	 * static final int RIGHT_FORKLIFT_LIMIT = 22;
	 * 
	 * //Finger ports public static final int RIGHT_FINGER_TALON = 5; //Motors
	 * public static final int LEFT_FINGER_TALON = 6; public static final int
	 * RIGHT_FINGER_ENCODER_A = 10; //Encoders public static final int
	 * RIGHT_FINGER_ENCODER_B = 11; public static final int
	 * LEFT_FINGER_ENCODER_A = 12; public static final int LEFT_FINGER_ENCODER_B
	 * = 13; public static final int RIGHT_FINGER_LIMIT = 14; //Limit Switches
	 * public static final int LEFT_FINGER_LIMIT = 15;
	 * 
	 * //Wedge ports /** public static final int LEFT_WEDGE_CHANNEL = 7;
	 * //Motors public static final int RIGHT_WEDGE_CHANNEL = 8; public static
	 * final int LEFT_WEDGE_LIMIT = 16; //Limit Switches public static final int
	 * RIGHT_WEDGE_LIMIT = 17; public static final int TRIANGLE_PEG_SOLENOID =
	 * 24;
	 * 
	 * //Collector ports public static final int RIGHT_COLLECTOR_WHEEL = 19;
	 * //Motors public static final int LEFT_COLLECTOR_WHEEL = 10; public static
	 * final int RIGHT_COLLECTOR_ANGLE_WHEEL = 11; public static final int
	 * LEFT_COLLECTOR_ANGLE_WHEEL = 12; public static final int
	 * LEFT_COLLECTOR_LIMIT = 18; //Limit Switches public static final int
	 * RIGHT_COLLECTOR_LIMIT = 19;
	 * 
	 * //Door ports public static final int LEFT_DOOR_CHANNEL = 20; //Motors
	 * public static final int RIGHT_DOOR_CHANNEL = 21;
	 */

	// CANTalons
	public static CANTalon rightFrontWheel;
	public static CANTalon leftFrontWheel;
	public static CANTalon rightBackWheel;
	public static CANTalon leftBackWheel;

	static void init() {
		rightFrontWheel = new CANTalon(FRONT_RIGHT_WHEEL_CHANNEL);
		leftFrontWheel = new CANTalon(FRONT_LEFT_WHEEL_CHANNEL);
		rightBackWheel = new CANTalon(REAR_RIGHT_WHEEL_CHANNEL);
		leftBackWheel = new CANTalon(REAR_LEFT_WHEEL_CHANNEL);
	}
}
