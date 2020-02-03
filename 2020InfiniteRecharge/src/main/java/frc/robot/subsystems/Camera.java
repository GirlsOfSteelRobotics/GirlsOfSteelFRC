package frc.robot.subsystems;

import frc.robot.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends SubsystemBase {

	public static final int FPS = 10;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;

	public CvSource processedStream;
	public UsbCamera driveCam;
	
	private Mat frame = new Mat();
	private VideoWriter videoWriter = new VideoWriter();
	private int framecount;

	public Camera() {
		// Start a stream for the camera viewed by the driver/operator
		driveCam = CameraServer.getInstance().startAutomaticCapture("Driver Camera", Constants.DRIVER_CAMERA);
		driveCam.setResolution(WIDTH, HEIGHT);
		driveCam.setFPS(20);
		driveCam.setBrightness(0);
		
		// // Create a Camera Server video stream of the given name using the logical camera number
		// visionCam = CameraServer.getInstance().startAutomaticCapture("Vision Camera", RobotMap.VISION_CAMERA);
		
		// // Adjust the camera settings; most important is to reduce the exposure very low
		// visionCam.setResolution(WIDTH, HEIGHT);
		// visionCam.setFPS(FPS);
		// visionCam.setExposureManual(24);
	
		// // Create a Camera Server stream that we'll fill with processed frames in GripPipelineListener
		// processedStream = CameraServer.getInstance().putVideo("Processed", 320, 240);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	
	public void openMovieFile() {
		// Create a filename based on the current date and time.
		// Avoid colons because they're illegal filename characters on some computers.
		// For example, Dec 25, 2019 at 10:15 am would be: 2019-12-25-10-15-00
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date = simpleDateFormat.format(new Date());

		// Note: the movie name MUST end with .avi if using the MJPG format!
		String filename = "/media/sda1/VisionCam-" + date + ".avi";
		int format = VideoWriter.fourcc('M', 'J', 'P', 'G');

		boolean result = videoWriter.open(filename, format, FPS, new Size(WIDTH, HEIGHT));

		if (result && videoWriter.isOpened())
			System.out.println("openMovieFile: opened file " + filename);
		else
			System.out.println("openMovieFile: FAILED to open " + filename + " (is USB drive attached?");
		framecount = 0;
	}

	public void addFrame(Mat frame) {
		if (videoWriter.isOpened()) {
			videoWriter.write(frame);
			framecount++;
		}
	}

	public void closeMovieFile() {
		if (videoWriter.isOpened()) {
			System.out.println("closeMovieFile: saving " + framecount/FPS + " sec of video");
			videoWriter.release();
		}
	}

	

}