package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int MAIN_MOTOR_TALON = 1;

	public static final int VISION_CAMERA = 0;

	public static final int BLINKIN_LEFT_PWM = 0;
	public static final int BLINKIN_RIGHT_PWM = 1;

	public static final int LIDAR_PWM = 2;
}
