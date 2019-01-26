package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends Subsystem {

  private UsbCamera driveCam; 
  private MjpegServer server;
	
	public Camera() {
	
	driveCam = new UsbCamera("driveCam", RobotMap.CAMERA_PORT);
	driveCam.setResolution(320, 240);
	driveCam.setFPS(20);
	CameraServer.getInstance().addCamera(driveCam);
    server = CameraServer.getInstance().addServer("CameraServer", 1181);
	server.setSource(driveCam);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}