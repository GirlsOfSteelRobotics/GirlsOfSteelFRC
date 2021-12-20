package org.usfirst.frc.team3504.robot.subsystems;


import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 */
public class Camera extends Subsystem {
    private final UsbCamera m_cam; //FIXME: How do you tell it which camera

    @SuppressWarnings("PMD.DoNotUseThreads")
    public Camera() {
        m_cam = new UsbCamera("camera", 0);

        VisionThread visionThread = new VisionThread(m_cam, new Pipeline(), pipeline -> System.out.println("Processed image"));
        visionThread.start();
    }

    private static class Pipeline implements VisionPipeline {

        //Constants: Need to check numbers
        private static final double[] TUBE_HUE_RANGE = {136, 182};
        private static final double[] TUBE_SATURATION_RANGE = {45, 255};
        private static final double[] TUBE_LUMINANCE_RANGE = {116, 255};

        private final Mat m_resultFrame;

        public Pipeline() {
            m_resultFrame = new Mat();
        }

        @Override
        public void process(Mat image) {
            hsvThreshold(image, TUBE_HUE_RANGE, TUBE_SATURATION_RANGE, TUBE_LUMINANCE_RANGE, m_resultFrame);
        }

        private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
                                  Mat out) {
            Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
            Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
                new Scalar(hue[1], sat[1], val[1]), out);
        }
    }

    public void sendToDashboard() {
//        server.startAutomaticCapture(cam);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.



    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
