package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.commands.camera.CameraOverlay;
import org.usfirst.frc.team3504.robot.commands.drive.DriveByJoystick;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * author Kriti
 */
public class Camera extends Subsystem {

	private int currentCamera;

	private CameraServer server;

	public Camera() {
		server = CameraServer.getInstance();
		server.setQuality(50);
		Cam1();
	}

	public void switchCam() {
		if (currentCamera == 0) {
			Cam1();
		} else {
			Cam0();
		}
	}

	public void Cam1() {
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		server.startAutomaticCapture("cam1");
		currentCamera = 1;
	}

	public void Cam0() {
		server.startAutomaticCapture("cam0");
		currentCamera = 0;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CameraOverlay());

	}
}
