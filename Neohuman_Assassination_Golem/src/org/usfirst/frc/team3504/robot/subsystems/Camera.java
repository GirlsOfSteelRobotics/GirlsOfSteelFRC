package org.usfirst.frc.team3504.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CameraServer server;
	private int camPivot;
	private int camFlap;
	private int curCam;
	Image frame;
	
	public Camera() {
		
		camPivot = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		camFlap = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		curCam = camPivot;
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		server = CameraServer.getInstance();
		server.setQuality(50);
		//server.startAutomaticCapture("cam0");

		
	}
	
	public void switchToCamFlap() {
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(camFlap);
		NIVision.IMAQdxStartAcquisition(camFlap);
		curCam = camFlap;
		NIVision.IMAQdxGrab(camFlap, frame, 1);
		server.setImage(frame);
	}
	
	public void switchToCamPivot() {
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(camPivot);
		NIVision.IMAQdxStartAcquisition(camPivot);
		curCam = camPivot;
		NIVision.IMAQdxGrab(camPivot, frame, 1);
		server.setImage(frame);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

