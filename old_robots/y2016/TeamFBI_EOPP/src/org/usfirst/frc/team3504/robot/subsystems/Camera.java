package org.usfirst.frc.team3504.robot.subsystems;

import java.util.Comparator;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveCommand;

import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ni.vision.NIVision;

//im pretty sure this code is all wrong...
public class Camera extends Subsystem {
	//int session = RobotMap.cameraSession;
	//Image frame = RobotMap.cameraFrame;
	//NIVision.IMAQdxConfigureGrab(session);
	
	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());	
    }
	
    
    public void operateCamera(boolean useCamera) {
       // NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);			

        //while (useCamera) {

          //  NIVision.IMAQdxGrab(session, frame, 1);
           // CameraServer.getInstance().setImage(frame);
            
            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
 
    
    public void stopCamera() {
    //	NIVision.IMAQdxStopAcquisition(session);
    }

}