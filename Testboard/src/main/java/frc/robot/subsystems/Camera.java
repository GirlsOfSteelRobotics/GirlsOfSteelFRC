package frc.robot.subsystems;

import frc.robot.RobotMap;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoWriter;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera extends Subsystem {

	public static final int FPS = 30;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;

	public CvSource processedStream;
	public UsbCamera visionCam;
	
	private Mat frame = new Mat();
	private VideoWriter videoWriter = null;

	public Camera() {
		// Create a Camera Server video stream of the given name using the logical camera number
		visionCam = CameraServer.getInstance().startAutomaticCapture("Raw Camera", RobotMap.VISION_CAMERA);
		// Adjust the camera settings; most important is to reduce the exposure very low
		visionCam.setResolution(WIDTH, HEIGHT);
		visionCam.setFPS(FPS);
		visionCam.setExposureManual(24);
		// Create a second Camera Server stream that we'll fill with processed frames in GripPipelineListener
		processedStream = CameraServer.getInstance().putVideo("Processed", 320, 240);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void openMovieFile() {
		System.out.println("openMovieFile Starting");
		videoWriter = new VideoWriter("/media/sda1/movie.mjpg", VideoWriter.fourcc('m', 'j', 'p', 'g'),
							FPS, new Size(WIDTH, HEIGHT));
	}

	public void addFrame(Mat frame) {
		if (videoWriter != null) {
			System.out.println("addFrame Starting");
			videoWriter.write(frame);
		}
		
	}

	public void closeMovieFile() {
		System.out.println("closeMovieFile Starting");
		if (videoWriter != null)
			videoWriter.release();
		videoWriter = null;
	}

	public Mat getVisionFrame() {
		CameraServer.getInstance().getVideo(visionCam).grabFrame(frame);
		return frame;
	}
}