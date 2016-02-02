package org.usfirst.frc.team3504.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public CameraServer server;
	private int camPivot;
	private int camFlap;
	private int curCam;
	private boolean frontCam;
	public Image frame;
	
	public Camera() {
		
		frontCam = true; //front = pivot side = true
		
		camPivot = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		camFlap = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		curCam = camPivot;
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		server = CameraServer.getInstance();
		server.setQuality(50);
		
		//getImage();
		//server.startAutomaticCapture("cam0");

		
		
	}
	
	/*  TODO: looking at documentation, this is what the format is supposed to look like, after that
	 *  the documentation becomes very vague, it doesn't give any ideas to what is supposed to
	 *  go in the method
	 */ 
	
	public void startAutomaticCamera(USBCamera camPivot){
		 
	}
	
	public void switchCam() {
		if (frontCam == true) {
			switchToCamFlap();
			 }
		else {
			switchToCamPivot();
			 }
		
	}
	
	public void switchToCamFlap() {
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(camFlap);
		NIVision.IMAQdxStartAcquisition(camFlap);
		curCam = camFlap;
		frontCam = false;
		getImage();
	}
	
	public void switchToCamPivot() {
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(camPivot);
		NIVision.IMAQdxStartAcquisition(camPivot);
		curCam = camPivot;
		frontCam = true;
		getImage();
	}
	
	public void getImage() {
		NIVision.IMAQdxGrab(curCam, frame, 1);
		server.setImage(frame);
	}
	
	void loop() {
		getImage(); 
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new UpdateCam());
    }
}

