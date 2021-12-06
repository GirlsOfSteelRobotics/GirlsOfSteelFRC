package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.vision.VisionRunner;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class GripPipelineListener implements VisionRunner.Listener<GripPipeline> {
    private static final Object CAMERA_LOCK = new Object();

    private double m_targetX;
    private double m_height;

    @Override
    public void copyPipelineOutputs(GripPipeline pipeline) {
        List<MatOfPoint> contours = pipeline.filterContoursOutput();
        synchronized (CAMERA_LOCK) {
            if (contours.size() == 2) {
                Rect r0 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                m_targetX = ((r0.x + (r0.width / 2.0)) + (r1.x + (r1.width / 2.0)))/2.0;
                m_height = (r0.height + r1.height)/2.0;
            }
            else {
                m_targetX = -1;
                m_height = -1;
            }
        }
    }

    public double getTargetX() {
        synchronized (CAMERA_LOCK) {
            return m_targetX;
        }
    }

    public double getHeight() {
        synchronized (CAMERA_LOCK) {
            return m_height;
        }
    }
}
