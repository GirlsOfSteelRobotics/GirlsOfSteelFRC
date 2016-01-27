package org.usfirst.frc.team3504.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CameraServer server;
	Image frame;
	//CameraServer camFlap;
	private int camFlap; //camFlap ID
	
	public Camera() {
		
		//server.setQuality(50);
		//server.startAutomaticCapture();
		//camArm.getInstance();
		//camArm.setQuality(50);
		//camArm.startAutomaticCapture();
		
		camFlap = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		NIVision.IMAQdxConfigureGrab(camFlap);
		NIVision.IMAQdxStartAcquisition(camFlap);
		server = CameraServer.getInstance();
		//camArm = NIVision.IMAQdxOpenCamera("camArm", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

