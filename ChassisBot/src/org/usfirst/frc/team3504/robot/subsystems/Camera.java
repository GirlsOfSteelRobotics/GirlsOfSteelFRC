package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.commands.camera.UpdateCam;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

/**
 *
 */
public class Camera extends Subsystem {
	
	private CameraServer server;
	private Image frame;
	private int onlyCam;
	private int curCam;
	private boolean frontCam;
	
	private static final int BAD_CAMERA = -1;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	try
    	{
    		onlyCam = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Camera() failed to open the camera (cam0)!!");
    		onlyCam = BAD_CAMERA;
    	}
    	if(onlyCam != BAD_CAMERA)
    		curCam = onlyCam;
    	
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	server = CameraServer.getInstance();
    	server.setQuality(50);
    	
    	if(curCam != BAD_CAMERA)
    	{
    		NIVision.IMAQdxStopAcquisition(curCam);
    		NIVision.IMAQdxConfigureGrab(curCam);
    		NIVision.IMAQdxStartAcquisition(curCam);
    		frontCam = true;
    		}
    	}
    	
    public void getImage()
    {
    	if(curCam != BAD_CAMERA)
    	{
    		NIVision.IMAQdxGrab(curCam,  frame,  1);
    		server.setImage(frame);
    	}
    }
    	
    public void initDefaultCommand()
    {
    	setDefaultCommand(new UpdateCam());
    }
}

