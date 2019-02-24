package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends Subsystem {

	private UsbCamera driveCam;
	public CvSource stream;
	public UsbCamera visionCam;

	public Camera() {
		visionCam = CameraServer.getInstance().startAutomaticCapture("Raw Camera", RobotMap.VISION_CAMERA);
		visionCam.setResolution(320, 240);
		visionCam.setFPS(10);
		visionCam.setExposureManual(16);
		stream = CameraServer.getInstance().putVideo("Processed", 320, 240);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}