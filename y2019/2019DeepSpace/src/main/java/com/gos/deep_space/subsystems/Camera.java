package com.gos.deep_space.subsystems;

import com.gos.deep_space.RobotMap;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Camera extends Subsystem {

    public static final int FPS = 10;
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;

    public CvSource m_processedStream;
    public UsbCamera m_visionCam;
    public UsbCamera m_driveCam;

    private final Mat m_frame = new Mat();
    private final VideoWriter m_videoWriter = new VideoWriter();
    private int m_framecount;

    public Camera() {
        // Start a stream for the camera viewed by the driver/operator
        m_driveCam = CameraServer.startAutomaticCapture("Driver Camera", RobotMap.DRIVER_CAMERA);
        m_driveCam.setResolution(WIDTH, HEIGHT);
        m_driveCam.setFPS(20);
        m_driveCam.setBrightness(0);

        // // Create a Camera Server video stream of the given name using the logical camera number
        // visionCam = CameraServer.startAutomaticCapture("Vision Camera", RobotMap.VISION_CAMERA);

        // // Adjust the camera settings; most important is to reduce the exposure very low
        // visionCam.setResolution(WIDTH, HEIGHT);
        // visionCam.setFPS(FPS);
        // visionCam.setExposureManual(24);

        // // Create a Camera Server stream that we'll fill with processed frames in GripPipelineListener
        // processedStream = CameraServer.putVideo("Processed", 320, 240);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }


    public void openMovieFile() {
        // Create a filename based on the current date and time.
        // Avoid colons because they're illegal filename characters on some computers.
        // For example, Dec 25, 2019 at 10:15 am would be: 2019-12-25-10-15-00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String date = simpleDateFormat.format(new Date());

        // Note: the movie name MUST end with .avi if using the MJPG format!
        String filename = "/media/sda1/VisionCam-" + date + ".avi";
        int format = VideoWriter.fourcc('M', 'J', 'P', 'G');

        boolean result = m_videoWriter.open(filename, format, FPS, new Size(WIDTH, HEIGHT));

        if (result && m_videoWriter.isOpened()) {
            System.out.println("openMovieFile: opened file " + filename);
        } else {
            System.out.println("openMovieFile: FAILED to open " + filename + " (is USB drive attached?");
        }
        m_framecount = 0;
    }

    public void addFrame(Mat frame) {
        if (m_videoWriter.isOpened()) {
            m_videoWriter.write(frame);
            m_framecount++;
        }
    }

    public void closeMovieFile() {
        if (m_videoWriter.isOpened()) {
            System.out.println("closeMovieFile: saving " + m_framecount / FPS + " sec of video");
            m_videoWriter.release();
        }
    }

    public Mat getVisionFrame() {
        CameraServer.getVideo(m_visionCam).grabFrame(m_frame);
        return m_frame;
    }

}
