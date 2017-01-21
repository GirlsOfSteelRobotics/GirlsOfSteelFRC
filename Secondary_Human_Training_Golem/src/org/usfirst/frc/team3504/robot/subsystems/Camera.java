package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	private CameraServer server;
	private UsbCamera camGear;
	private UsbCamera camClimb;
	private int curCam;
	private boolean frontCam;
	private MjpegServer mjpegServer;

	// If a camera can't be opened, set its variable to this value.
	// We're assuming the OpenCamera method will never return this value
	// if a camera is found successfully. (The API docs don't clarify.)
	private static final int BAD_CAMERA = -1;

	public Camera() {
		// Attempt to open the cameras, but fail gracefully if they aren't found.
		// Upon error, set the camera variable to the BAD_CAMERA constant defined above.
		camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
		camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
		mjpegServer = new MjpegServer("mjpegServer", 1181); //sink that gets fed to
		mjpegServer.setSource(camGear); //usbcam feeds frames to server
		
		/*
		try {
			camClaw = NIVision.IMAQdxOpenCamera("cam0", 
					NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		} catch (Exception ex) {
			System.out.println("Camera() failed to open the claw camera (cam0)!!");
			camClaw = BAD_CAMERA;
		}
		try {
			camFlap = NIVision.IMAQdxOpenCamera("cam1", 
					NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		} catch (Exception ex){
			System.out.println("Camera() failed to open the flap camera (cam1)!!");
			camFlap = BAD_CAMERA;
		}
        */
		// Set the default camera here, skipping to the secondary camera if needed.
		// Also set the frontCam boolean (front = claw side = true)
		/*
		if (camGear != BAD_CAMERA)
			curCam = camGear;
		else if (camClimb != BAD_CAMERA)
			curCam = camClimb;
		else
			curCam = BAD_CAMERA;
		
		server = CameraServer.getInstance();
		server.setQuality(50);
		switchToCamera(curCam);
		*/
		// Don't call startAutomaticCapture() here because we're using setImage() instead
	}

	/* public void switchCam() {
		if (frontCam == true)
			switchToCamFlap();
		else
			switchToCamClaw();
	}
	*/
	
	public void switchToCamClimb() {
		switchToCamera(camClimb);
	}

	public void switchToCamGear() {
		switchToCamera(camGear);
	}

	/* Private method used to avoid duplicating the code in two places */
	private void switchToCamera(UsbCamera newCam) {
		// Don't do anything if the desired camera wasn't found
		mjpegServer.setSource(camGear);
	/*	if (newCam != BAD_CAMERA) {
			// Stop any camera that's running right now
			if (curCam != BAD_CAMERA)
				mjpegServer.setSource(camGear);
			curCam = newCam;
			frontCam = (newCam == camGear);
			// Get an initial image and display it.
			// We need to run getImage() regularly elsewhere in the code
			// in order to get a continuous feed. See the default command below.
			getImage();
		}
	*/	
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

