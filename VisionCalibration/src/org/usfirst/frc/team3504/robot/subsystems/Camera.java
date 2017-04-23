package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private UsbCamera cam;

	public Camera() {
		cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(320, 240);
		cam.setFPS(10);
		cam.setExposureManual(48);
		//CameraServer.getInstance().addCamera(cam);
		//server = CameraServer.getInstance().addServer("CameraServer", 1181);
		//server.setSource(cam);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}