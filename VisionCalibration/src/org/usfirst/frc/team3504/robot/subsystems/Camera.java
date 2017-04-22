package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private UsbCamera cam;
	//private MjpegServer server;

	public Camera() {
		cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(320, 240);
		cam.setFPS(10);
		//CameraServer.getInstance().addCamera(cam);
		//server = CameraServer.getInstance().addServer("CameraServer", 1181);
		//server.setSource(cam);

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