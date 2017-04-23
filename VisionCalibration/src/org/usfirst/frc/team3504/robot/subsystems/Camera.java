package org.usfirst.frc.team3504.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem for the default USB camera
 */
public class Camera extends Subsystem {

	private UsbCamera cam;
	CvSink cvSink;

	/**
	 * Create the camera subsystem. Enables the camera server stream to the
	 * dashboard so it will run even before the robot is enabled. Also sets the
	 * resolution and frame rate.
	 */
	public Camera() {
		cam = CameraServer.getInstance().startAutomaticCapture();
		cam.setResolution(320, 240);
		cam.setFPS(10);
		cvSink = CameraServer.getInstance().getVideo();
	}

	/**
	 * Set the manual exposure level of the camera
	 * 
	 * @param exposurePercent
	 *            The exposure level as a percentage from 0 to 100
	 */
	public void setExposure(int exposurePercent) {
		System.out.println("Setting exposure to " + exposurePercent + " percent");
		cam.setExposureManual(exposurePercent);
	}

	/**
	 * Capture a frame and save it to the given filename on the RoboRIO. Several
	 * frames are captured to make sure that we get one based on the current
	 * exposure setting.
	 * 
	 * @param filename
	 *            Full pathname of the image file to write. The file format is
	 *            implied by the extension (.jpg, .png, etc.)
	 */
	public void saveImage(String filename) {

		Mat mat = new Mat();
		System.out.println("Saving image to: " + filename);
		// Changing the exposure isn't instantaneous, so discard a couple of
		// frames to make sure we've got a frame that is using the new setting
		for (int i = 0; i < 5; i++) {
			if (cvSink.grabFrame(mat) == 0) {
				// System.err.format("Failed to grab frame number %d from
				// camera!!\n", i);
			}
		}
		Imgcodecs.imwrite(filename, mat);
	}

	/**
	 *  No default command needed for this subsystem
	 */
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
