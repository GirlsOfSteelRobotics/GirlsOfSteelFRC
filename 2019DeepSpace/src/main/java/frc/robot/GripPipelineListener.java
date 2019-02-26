/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.vision.VisionRunner;

/**
 * Add your docs here.
 */
public class GripPipelineListener implements VisionRunner.Listener<GripPipeline> {
    public Object cameraLock = new Object();
    
	public double targetX; 
    public double height; 
    
    public void copyPipelineOutputs(GripPipeline pipeline) {
		// Get a frame of video from the last step of the pipeline that deals with video 
		// (before converting to a list of contours) and send it to the Processed stream
		Mat frame = pipeline.cvErodeOutput();
		Robot.camera.processedStream.putFrame(frame);

		ArrayList<MatOfPoint> contours = pipeline.filterContoursOutput();
		//System.out.println("GripPipelineListener contours.size: " + contours.size());
		synchronized (cameraLock) {
			if (contours.size() == 2) {
				Rect r0 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
				targetX = ((r0.x + (r0.width / 2.0)) + (r1.x + (r1.width / 2.0)))/2.0;
				height = (r0.height + r1.height)/2.0;
			} 
			else {
				targetX = -1;
				height = -1;
			}
		}
	}
}
