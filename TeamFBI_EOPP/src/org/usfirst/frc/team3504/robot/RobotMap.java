package org.usfirst.frc.team3504.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.vision.USBCamera;

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
		
	public static CANTalon driveSystemDriveLeft0;
	public static CANTalon driveSystemDriveRight0;
	public static CANTalon driveSystemDriveLeft1;
	public static CANTalon driveSystemDriveRight1;
	public static CANTalon driveSystemDriveLeft3;
	public static CANTalon driveSystemDriveRight3;
	
	public static RobotDrive driveRobotDrive;
	
	public static DoubleSolenoid shiftersShifterLeft;
	public static DoubleSolenoid shiftersShifterRight;
	
	public static CANTalon conveyorBeltMotorRight;
	public static CANTalon conveyorBeltMotorLeft;
	
	//public static Image cameraFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	//public static int cameraSession = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	
} 
