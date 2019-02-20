package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends Subsystem {

	public UsbCamera driveCam;
	public MjpegServer server;
	public CvSource outputStream;

	public Camera() {
		driveCam = CameraServer.getInstance().startAutomaticCapture();
		// driveCam = new UsbCamera("driveCam", RobotMap.CAMERA_PORT);
		driveCam.setResolution(640, 480);
		driveCam.setFPS(20);
		driveCam.setExposureManual(16);
		// CameraServer.getInstance().addCamera(driveCam);
		// server = CameraServer.getInstance().addServer("CameraServer", 1181);
		// server.setSource(driveCam);

		outputStream = CameraServer.getInstance().putVideo("Processed", 640, 480);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}