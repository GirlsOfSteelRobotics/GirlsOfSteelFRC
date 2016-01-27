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
	//Image frame;
	//CameraServer camFlap;
	//private int camFlap; //camFlap ID
	
	public Camera() {
		
		server.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

