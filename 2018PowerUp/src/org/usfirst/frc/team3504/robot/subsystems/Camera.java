package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {

	private UsbCamera driveCam; 
	private MjpegServer server;
	
	
	public Camera() {
		driveCam = new UsbCamera("camGear", RobotMap.DRIVING_CAM);
		driveCam.setResolution(320, 240);
		driveCam.setFPS(10);
		CameraServer.getInstance().addCamera(driveCam);
		server = CameraServer.getInstance().addServer("CameraServer", 1181);
		server.setSource(driveCam);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}