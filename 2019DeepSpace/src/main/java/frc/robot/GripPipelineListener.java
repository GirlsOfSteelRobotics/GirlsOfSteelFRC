/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.RotatedRect;

import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.wpilibj.util.SortedVector.Comparator;

/**
 * Add your docs here.
 */
public class GripPipelineListener implements VisionRunner.Listener<GripPipeline> {
    public Object cameraLock = new Object();
    
	public double targetX; 
	public double height; 
	public double angle; 
	public ArrayList<MatOfPoint> contours; 
	public ArrayList<RotatedRect> rotatedRects; 
    
    public void copyPipelineOutputs(GripPipeline pipeline) {
		//record vision camera movie
		Robot.camera.addFrame(Robot.camera.getVisionFrame());
		// Get a frame of video from the last step of the pipeline that deals with video 
		// (before converting to a list of contours) and send it to the Processed stream
		Mat frame = pipeline.hslThresholdOutput();

		contours = pipeline.filterContoursOutput();
		//System.out.println("GripPipelineListener contours.size: " + contours.size());
		synchronized (cameraLock) {
			for(int i = 0; i < contours.size(); i++){
				rotatedRects.add(Imgproc.fitEllipseDirect(contours.get(i))); 

			}


			
			rotatedRects.sort(Comparator.comparing(p -> p.x)); 

			targetX = (r0.center.x +  r1.center.x)/2.0;
			height = (r0.size.height + r1.size.height)/2.0;
			angle = r0.angle; 

			// else {
			// 	targetX = -1;
			// 	height = -1;
			// 	angle = -1; 
			// }
		}

		Imgproc.circle(frame, r0.center, 6, (255, 255, 255)); 
		Robot.camera.processedStream.putFrame(frame);

	}
}
