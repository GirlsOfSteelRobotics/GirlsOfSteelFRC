package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * author Kriti
 */
public class Camera extends Subsystem {
 
	private String name;
	private String camera0 = "cam0";
	private String camera1 = "cam1";
	
	
	public Camera()
	{
		Cam0();
	}

    public void switchCam() {
    	if (name==camera1)
    		Cam0();
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
    
    public void Cam0() {
        CameraServer server;
        server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");
        name = camera0;
        //SmartDashboard.putData("cam0", (Sendable) CameraServer.getInstance());
}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}


