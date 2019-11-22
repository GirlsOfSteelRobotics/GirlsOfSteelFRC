package org.usfirst.frc.team3504.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static final int DRIVE_RIGHT_A_CAN_ID = 1;
	public static final int DRIVE_RIGHT_B_CAN_ID = 2;
	public static final int DRIVE_LEFT_A_CAN_ID = 3;
	public static final int DRIVE_LEFT_B_CAN_ID = 4;

	public static final int DRIVE_LEFT_ENCODER_A = 0;
	public static final int DRIVE_LEFT_ENCODER_B = 1;
	public static final int DRIVE_RIGHT_ENCODER_A = 2;
	public static final int DRIVE_RIGHT_ENCODER_B = 3;

	// Encoder-to-distance constants
	// How many ticks are there on the encoder wheel?
	private static final double pulsePerRevolution = 360;
	// How far to we travel when the encoder turns one full revolution?
	// Gear ratio is turns of the wheel per turns of the encoder
	// FIXME - gear ratio is just a swag
	private static final double distancePerRevolution = 8.0/* wheel size */ * Math.PI * (16 / 16)/* gear ratio */;
	// Given our set of wheels and gear box, how many inches do we travel per pulse?
	public static final double DISTANCE_PER_PULSE = distancePerRevolution / pulsePerRevolution;
}
