package com.gos.steam_works;

import edu.wpi.first.vision.VisionRunner;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

@SuppressWarnings("PMD")
public class GripPipelineListener implements VisionRunner.Listener<GripPipeline> {
    public static Object cameraLock = new Object();

    public double targetX;
    public double height;

    @Override
    public void copyPipelineOutputs(GripPipeline pipeline) {
        ArrayList<MatOfPoint> contours = pipeline.filterContoursOutput();
        synchronized (cameraLock) {
            if (contours.size() == 2) {
                Rect r0 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                targetX = ((r0.x + (r0.width / 2.0)) + (r1.x + (r1.width / 2.0))) / 2.0;
                height = (r0.height + r1.height) / 2.0;
            } else {
                targetX = -1;
                height = -1;
            }
        }
    }
}
