package org.usfirst.frc.team3504.robot.commands.camera;
import org.usfirst.frc.team3504.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CameraOverlay extends Command {

	private int session;
	private Image frame; 
	
	private NIVision.Rect top; 
    private NIVision.Rect side; 
	
    public CameraOverlay() {
    	
    	requires(Robot.camera);
    	
    	top = new NIVision.Rect(296, 0, 14, 384);
    	side = new NIVision.Rect(296, 384, (480-296), 14);
    	
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
        NIVision.IMAQdxStartAcquisition(session);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqDrawShapeOnImage(frame, frame, top,
                DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 255.0f/* this number makes the line red*/); 
        NIVision.imaqDrawShapeOnImage(frame, frame, side, 
        		DrawMode.PAINT_VALUE, ShapeMode.SHAPE_RECT, 255.0f);
        CameraServer.getInstance().setImage(frame);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	NIVision.IMAQdxStopAcquisition(session);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
