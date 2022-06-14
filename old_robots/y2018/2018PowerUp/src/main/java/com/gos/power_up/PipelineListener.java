package com.gos.power_up;

import edu.wpi.first.vision.VisionRunner;
import org.opencv.core.KeyPoint;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PMD")
public class PipelineListener implements VisionRunner.Listener<Pipeline> {
    public Object cameraLock = new Object();

    public double goalLength = Blobs.MIN_BLOBS_FOR_LINE;
    public static ArrayList<Blob> blobList = new ArrayList<>();

    @Override
    public void copyPipelineOutputs(Pipeline pipeline) {
        MatOfKeyPoint blobs = (pipeline.findBlobsOutput());
        synchronized (cameraLock) {
            List<KeyPoint> gripBlobs = blobs.toList();
            ArrayList<Blob> returnBlobs = new ArrayList<>();
            for (int i = 0; i < gripBlobs.size(); i++) {
                Point blob = gripBlobs.get(i).pt;
                returnBlobs.add(new Blob(blob.x, blob.y));
            }
            blobList = returnBlobs;
        }
    }

}
