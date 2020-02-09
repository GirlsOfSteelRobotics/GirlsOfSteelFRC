package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 *
 */
public class Camera extends Subsystem {

	private UsbCamera camIntake;
	private UsbCamera camClimb;
	private VideoSink switchedCam;
	
	public Camera() {

		if(RobotBase.isSimulation()){
			return; 
		}
		camIntake = new UsbCamera("camIntake", Constants.CAMERA_INTAKE);
		camIntake.setResolution(320, 240);
		camIntake.setFPS(10);
		camClimb = new UsbCamera("camClimb", Constants.CAMERA_CLIMB);
		camClimb.setResolution(320, 240);
		camClimb.setFPS(10);
		switchedCam = CameraServer.getInstance().addSwitchedCamera("Switched Camera");
		switchedCam.setSource(camIntake);

		// To see the stream in smartdashboard, add a CameraServer Stream Viewer
	}

	public void switchToCamClimb() {

		if(RobotBase.isSimulation()){
			return; 
		}
		switchedCam.setSource(camClimb);
		System.out.println("Cam Climb!");
	}

	public void switchToCamIntake() {

		if(RobotBase.isSimulation()){
			return; 
		}

		switchedCam.setSource(camIntake);
		System.out.println("Cam Intake!");
	}

	public void initDefaultCommand() {
	}
}