package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends Subsystem {

  private UsbCamera driveCam; 
  public MjpegServer server;
  public UsbCamera visionCam; 
	
	public Camera() {
	
	visionCam = new UsbCamera("visionCam", RobotMap.VISION_CAMERA);
	visionCam.setResolution(320, 240);
	visionCam.setFPS(10); 
	visionCam.setExposureManual(16);
	CameraServer.getInstance().addCamera(driveCam);
    server = CameraServer.getInstance().addServer("CameraServer", 1181);
	server.setSource(driveCam);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}