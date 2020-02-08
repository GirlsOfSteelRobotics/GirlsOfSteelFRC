package frc.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 *
 */
public class Camera extends SubsystemBase {

	private UsbCamera camIntake;
	private UsbCamera camClimb;
	private MjpegServer server;
	
	
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
		CameraServer.getInstance().addCamera(camIntake);
		CameraServer.getInstance().addCamera(camClimb);
		server = CameraServer.getInstance().addServer("CameraServer", 1181);
		server.setSource(camIntake);

		// For stream in smartdashboard add a mjpg stream viewer,
		// right click, select properties, and add
		// http://roborio-3504-frc.local:1181/stream.mjpg
		// as the URL
	}

	public void switchToCamClimb() {

		if(RobotBase.isSimulation()){
			return; 
		}
		server.setSource(camClimb);
		System.out.println("Cam Climb!");
	}

	public void switchToCamIntake() {

		if(RobotBase.isSimulation()){
			return; 
		}

		server.setSource(camIntake);
		System.out.println("Cam Intake!");
	}

	public void initDefaultCommand() {

		if(RobotBase.isSimulation()){
			return; 
		}
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}


}