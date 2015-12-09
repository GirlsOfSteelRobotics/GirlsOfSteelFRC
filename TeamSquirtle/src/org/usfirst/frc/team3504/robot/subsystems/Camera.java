package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
	CameraServer server;
	USBCamera cam;
	public Camera() {
		server = CameraServer.getInstance();
		server.setQuality(50);
		cam = new USBCamera();
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		server.startAutomaticCapture("cam0");
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void findTube() {
		cam.getImageData(null);
		
	}


	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
