package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private CameraServer server;
	private UsbCamera camGear;
	private UsbCamera camClimb;

	public Camera() {
		//camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
		//camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
	}

	public void switchToCamClimb() {
		CameraServer.getInstance().removeCamera("camGear");
		CameraServer.getInstance().startAutomaticCapture("camClimb", RobotMap.CAMERA_CLIMB);
		System.out.println("Cam Climb!");
	}

	public void switchToCamGear() {
		CameraServer.getInstance().removeCamera("camClimb");
		CameraServer.getInstance().startAutomaticCapture("camGear", RobotMap.CAMERA_GEAR);
		System.out.println("Cam Gear!");
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

