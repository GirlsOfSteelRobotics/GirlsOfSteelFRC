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
import org.opencv.imgproc.Imgproc;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;

import edu.wpi.first.vision.VisionRunner;

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
	public ArrayList<TargetPair> targetPairs;
	public TargetPair goalTargetPair;

	// height distance ratio: (rotated height of the tape which is 5.5 inches) / distance between center of masses of two pieces of tape
	public final double HD_RATIO = 0.5947498932; // (5.5) / (8 + 2(sin(14.5))(2.75 - tan(14.5))) 
												// calculated by ziya, vivian, and meghna 
												// whose grandfather invented the number 0 which made this problem possible
	public final double HD_RATIO_ERROR = 0.1 * HD_RATIO;

	public void copyPipelineOutputs(GripPipeline pipeline) {
		// record vision camera movie
		Robot.camera.addFrame(Robot.camera.getVisionFrame());
		// Get a frame of video from the last step of the pipeline that deals with video
		// (before converting to a list of contours) and send it to the Processed stream
		Mat frame = pipeline.hslThresholdOutput();

		contours = pipeline.filterContoursOutput();
		// System.out.println("GripPipelineListener contours.size: " + contours.size());
		synchronized (cameraLock) {

			// creates array of rotated rectangles
			for (int i = 0; i < contours.size(); i++) {
				rotatedRects.add(Imgproc.fitEllipseDirect(contours.get(i)));
			}

			// sorts array of rotated rectangles by x coordinate
			rotatedRects = sortRotatedRects(rotatedRects);

			// for testing/debugging: prints out x coordinated of sorted rotated rects array
			System.out.println("Sorted X-Coordinates: ");
			for (RotatedRect x: rotatedRects) {
				System.out.print(x.center.x + " ");
			}

			// adds pairs of tape that are angled towards each other and a certain distance apart to array of Target Pair
			for (int i = 0; i < contours.size() - 1; i++) {
				TargetPair pair = new TargetPair(rotatedRects.get(i), rotatedRects.get(i + 1));
				double heightDistanceRatio = pair.getHeight()/pair.getPairDistance();
				double heightDistanceRatioError = HD_RATIO - heightDistanceRatio;

				if (rotatedRects.get(i).angle < 0 && rotatedRects.get(i + 1).angle > 0
					&& heightDistanceRatioError < HD_RATIO_ERROR) { // angle < 0 is left target, angle > 0 is right target
					targetPairs.add(pair);
				}
			}

			// sorts target pairs by area of their contours in ascending order
			targetPairs = sortTargetPairs(targetPairs);

			// for testing/debugging: prints out average areas of sorted target pairs array
			System.out.println("Sorted Target Areas: ");
			for (TargetPair x: targetPairs) {
				System.out.print(x.getTargetAverageArea() + " ");
			}

			// goal pair is the closest (the pair with the largest area)
			if (targetPairs.size() <= 0) {
				targetX = -1;
				height = -1;
			} else {
				goalTargetPair = targetPairs.get(targetPairs.size() - 1);
				targetX = goalTargetPair.getTargetCenterX();
				height = goalTargetPair.getHeight();
			}
		}

		// @Joe why does the editor catch this error first?
		// puts a dot in the middle of target pair on the processed image for SmartDashboard
		Imgproc.circle(frame, goalTargetPair.getTargetCenterPoint(), 6, new Scalar(255, 255, 255));
		Robot.camera.processedStream.putFrame(frame);

	}

	public ArrayList<RotatedRect> sortRotatedRects(ArrayList<RotatedRect> arr) {
		for (int i = 0; i < arr.size() - 1; i++) {
			int min = i;
			for (int j = i + 1; j < arr.size(); j++) {
				if (arr.get(j).center.x < arr.get(min).center.x)
					min = j;
			}

			RotatedRect temp = arr.get(i);
			arr.set(i, arr.get(min));
			arr.set(min, temp);
		}

		return arr;
	}

	public ArrayList<TargetPair> sortTargetPairs(ArrayList<TargetPair> arr) {
		for (int i = 0; i < arr.size() - 1; i++) {
			int min = i;
			for (int j = i + 1; j < arr.size(); j++) {
				if (arr.get(j).getTargetAverageArea() < arr.get(min).getTargetAverageArea())
					min = j;
			}

			TargetPair temp = arr.get(i);
			arr.set(i, arr.get(min));
			arr.set(min, temp);
		}

		return arr;
	}
}
