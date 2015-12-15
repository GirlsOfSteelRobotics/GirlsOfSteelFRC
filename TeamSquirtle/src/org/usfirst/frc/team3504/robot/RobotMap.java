package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

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
	
	public static CANTalon rightTalon1;
	public static CANTalon leftTalon1;
	public static CANTalon rightTalon2;
	public static CANTalon leftTalon2;
	public static CANTalon rightTalon3;
	public static CANTalon leftTalon3;
	public static RobotDrive driveSystem;
	public static CANTalon conveyorTalon1;
	public static CANTalon conveyorTalon2;
	
	static void init() {
		rightTalon1 = new CANTalon(4);
		leftTalon1 = new CANTalon(0);
		rightTalon2 = new CANTalon(11);
		leftTalon2 = new CANTalon(1);
		rightTalon3 = new CANTalon(13);
		leftTalon3 = new CANTalon(2);
		driveSystem = new RobotDrive(rightTalon1, leftTalon1);
		conveyorTalon1 = new CANTalon(6);
		conveyorTalon2 = new CANTalon(7);
		
	}
}
