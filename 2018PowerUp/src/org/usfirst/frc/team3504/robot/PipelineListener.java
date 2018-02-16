package org.usfirst.frc.team3504.robot;

import java.util.List;
import java.util.ArrayList;

import org.opencv.core.KeyPoint;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3504.robot.subsystems.Blobs;

import edu.wpi.first.wpilibj.vision.VisionRunner;

public class PipelineListener implements VisionRunner.Listener<Pipeline> {
	public Object cameraLock = new Object(); 

	public double goalLength = Blobs.MIN_BLOBS_FOR_LINE;
	public static ArrayList<Blob> blobList = new ArrayList<Blob>();

	public void copyPipelineOutputs(Pipeline pipeline) {
		MatOfKeyPoint blobs = (pipeline.findBlobsOutput());
		synchronized (cameraLock) {
			List<KeyPoint> blobsList = blobs.toList();
			ArrayList<Blob> returnBlobs = new ArrayList<Blob>();
			for (int i = 0; i < blobsList.size(); i++){
				double x = blobsList.get(i).pt.x;
				double y = blobsList.get(i).pt.y;
				returnBlobs.add(new Blob(x,y));
			}
			blobList = returnBlobs;

		}
	}

	public ArrayList<Blob> gimmeBlobs(Pipeline pipeline) {
		MatOfKeyPoint blobs = (pipeline.findBlobsOutput());
		ArrayList<Blob> returnBlobs = new ArrayList<Blob>();
		synchronized (cameraLock) {
			List<KeyPoint> blobList = blobs.toList();
			for (int i = 0; i < blobList.size(); i++){
				double x = blobList.get(i).pt.x;
				double y = blobList.get(i).pt.y;
				returnBlobs.add(new Blob(x,y));
			}
		}

		return returnBlobs;
	}
}
