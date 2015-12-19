package org.usfirst.frc.team3504.robot.subsystems;

import java.nio.ByteBuffer;
import java.util.Comparator;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem {
	CameraServer server;

	USBCamera cam; //FIXME: How do you tell it which camera 
	Image frame; 
	Image resultFrame; 
	
	//Constants: Need to check numbers 
	NIVision.Range TUBE_HUE_RANGE = new NIVision.Range(136, 182);
	NIVision.Range TUBE_SATURATION_RANGE = new NIVision.Range(45, 255);
	NIVision.Range TUBE_LUMINANCE_RANGE = new NIVision.Range(116, 255);

	public Camera() {

		server = CameraServer.getInstance();
		server.setQuality(50);


		server = CameraServer.getInstance();  
		cam = new USBCamera(); 
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, 0);
		resultFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);


		// the camera name (ex "cam0") can be found through the roborio web
		// interface
	}

	public void processImage() {
		cam.getImage(frame);
		NIVision.imaqColorThreshold(resultFrame, frame, 0, NIVision.ColorMode.HSL, TUBE_HUE_RANGE, TUBE_SATURATION_RANGE, TUBE_LUMINANCE_RANGE);
		//FINISHED HERE: test to see what happens, does it mask? 
	}
	
	public void sendToDashboard() {
		server.startAutomaticCapture(cam);
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
