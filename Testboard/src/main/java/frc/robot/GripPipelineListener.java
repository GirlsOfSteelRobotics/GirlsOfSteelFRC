/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import frc.robot.subsystems.*;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionRunner;


/**
 * Add your docs here.
 */
public class GripPipelineListener implements VisionRunner.Listener<GripPipeline>{
	public Object cameraLock = new Object();

    
	public double targetX; 
    public double height; 
    
    public void copyPipelineOutputs(GripPipeline pipeline) {

		ArrayList<MatOfPoint> contours = pipeline.filterContoursOutput();
		System.out.println("contours made");
		System.out.println("countours.size = " + contours.size());

		Mat image = pipeline.cvErodeOutput(); 
		//Mat source = pipeline.resizeImageOutput(); 

		// CvSink cvSink = CameraServer.getInstance().getVideo();
		// CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);

		// cvSink.grabFrame(source); 
		// Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY); 
		Robot.camera.outputStream.putFrame(image); 

		synchronized (cameraLock) {
			System.out.println("sychronized");
			System.out.println("countours.size = " + contours.size());
			if (contours.size() == 2) {
				Rect r0 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
				targetX = ((r0.x + (r0.width / 2.0)) + (r1.x + (r1.width / 2.0)))/2.0;
				height = (r0.height + r1.height)/2.0;
				System.out.println("iftargetX: " + targetX);
				System.out.println("ifheight: " + height);
			} 
			else {
				targetX = -1;
				height = -1;
				System.out.println("elsetargetX: " + targetX);
				System.out.println("elseheight: " + height);
			}
			System.out.println("targetX: " + targetX);
			System.out.println("height: " + height);

		}
	}
}
