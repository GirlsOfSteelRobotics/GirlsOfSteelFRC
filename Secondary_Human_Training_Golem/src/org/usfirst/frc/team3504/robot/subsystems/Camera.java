package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private UsbCamera camGear;
	private UsbCamera camClimb;
	private MjpegServer server;

	public Camera() {
		camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
		camGear.setResolution(320, 240);
		camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
		camClimb.setResolution(320, 240);
		//CameraServer.getInstance().startAutomaticCapture(camGear);
		CameraServer.getInstance().addCamera(camGear);
		server = CameraServer.getInstance().addServer("CameraServer", 1181);
		server.setSource(camGear);
		
		//CameraServer.getInstance().removeServer(server.getName());
	}

	public void switchToCamClimb() {
		//CameraServer.getInstance().removeCamera("camGear");
		CameraServer.getInstance().startAutomaticCapture(camClimb);
		System.out.println("Cam Climb!");
	}

	public void switchToCamGear() {
		//CameraServer.getInstance().removeCamera("camClimb");
		CameraServer.getInstance().startAutomaticCapture(camGear);
		System.out.println("Cam Gear!");
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

