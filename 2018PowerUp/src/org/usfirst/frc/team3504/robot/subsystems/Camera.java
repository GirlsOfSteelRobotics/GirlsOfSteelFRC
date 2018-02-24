package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends Subsystem {

	private UsbCamera driveCam; 
	private MjpegServer server;

	
	public Camera() {
		SmartDashboard.putData("CAMERA", this);
		
		driveCam = new UsbCamera("camGear", RobotMap.DRIVING_CAM);
		driveCam.setResolution(320, 240);
		driveCam.setFPS(30);
		CameraServer.getInstance().addCamera(driveCam);
		server = CameraServer.getInstance().addServer("CameraServer", 1181);
		server.setSource(driveCam);
		
		// For stream in smartdashboard add a mjpg stream viewer,
		// right click, select properties, and add
		// http://roborio-3504-frc.local:1181/stream.mjpg
		// as the URL
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}