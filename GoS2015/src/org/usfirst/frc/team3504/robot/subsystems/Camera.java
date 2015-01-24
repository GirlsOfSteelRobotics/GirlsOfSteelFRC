package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap.*;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//private camera camera1
	//private camera camera2 (?) check 
 
	private String name;
	private String camera2 = "cam2";
	private String camera1 = "cam1";
	
	
	public Camera()
	{
		Cam2();
	}
	
	//method for stuff from robot.java

    public void switchCam() {
    	if (name==camera1)
    		Cam2();
    	else
    		Cam1();
    }

    
    
    public void Cam1() {
        CameraServer server;
        server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam1");
        name = camera1;
       // SmartDashboard.putData("cam1", (Sendable) CameraServer.getInstance());
    }
    
    public void Cam2() {
        CameraServer server;
        server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam2");
        name = camera2;
        //SmartDashboard.putData("cam2", (Sendable) CameraServer.getInstance());
}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}


