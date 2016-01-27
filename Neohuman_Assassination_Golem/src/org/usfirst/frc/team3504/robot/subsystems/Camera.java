package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CameraServer camFlap;
	CameraServer camArm;

	public Camera() {
		camFlap.getInstance();
		camFlap.setQuality(50);
		camFlap.startAutomaticCapture();
		camArm.getInstance();
		camArm.setQuality(50);
		camArm.startAutomaticCapture();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

