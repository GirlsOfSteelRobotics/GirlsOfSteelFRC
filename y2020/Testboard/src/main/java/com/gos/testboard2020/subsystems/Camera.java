package com.gos.testboard2020.subsystems;

import com.gos.testboard2020.Constants;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Camera extends SubsystemBase {

	private UsbCamera camIntake;
	private UsbCamera camClimb;
	private VideoSink server;

	public Camera() {
		if (RobotBase.isSimulation()) {
			return;
		}
		camIntake = new UsbCamera("camIntake", Constants.CAMERA_INTAKE);
		camIntake.setResolution(320, 240);
		camIntake.setFPS(20);
		camClimb = new UsbCamera("camClimb", Constants.CAMERA_CLIMB);
		camClimb.setResolution(320, 240);
		camClimb.setFPS(20);
		server = CameraServer.getInstance().addSwitchedCamera("Driver Cameras");
		server.setSource(camIntake);

		// To see the stream in the Dashboard, add a CameraServer Stream Viewer
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
		if (RobotBase.isSimulation()) {
			return;
		}
	}

	public void switchToCamClimb() {
		if (RobotBase.isSimulation()) {
			return;
		}
		server.setSource(camClimb);
		System.out.println("Cam Climb!");
	}

	public void switchToCamIntake() {
		if (RobotBase.isSimulation()) {
			return;
		}

		server.setSource(camIntake);
		System.out.println("Cam Intake!");
	}
}
