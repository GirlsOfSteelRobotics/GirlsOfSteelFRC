package org.usfirst.frc.team3504.robot;

import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
//import com.mindsensors.CANLight;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Climber;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Chassis chassis;
	public static Shifters shifters;
	public static Agitator agitator;
	public static Climber climber;
	public static Shooter shooter;
	public static Camera camera;
	public static Loader loader;
	public static GripPipelineListener listener;

	Command autonomousCommand;
	
	private VisionThread visionThread; 

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		chassis = new Chassis();
		shifters = new Shifters();
		agitator = new Agitator();
		climber = new Climber();
		shooter = new Shooter();
		camera = new Camera();
		loader = new Loader();
		listener = new GripPipelineListener();

		// Initialize all subsystems before creating the OI
		oi = new OI();
/*
		try {
			@SuppressWarnings("unused")
			Process p;
			p = new ProcessBuilder("chmod", "+x", "/home/lvuser/GRIPonRoboRIO")
					.start();
			p = new ProcessBuilder("/home/lvuser/GRIPonRoboRIO", "-f", "20",
					"-e", "8").start();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		    
		visionThread = new VisionThread(camera.visionCam, new GripPipeline(), listener);
		visionThread.start();
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		System.out.println("DisabledInit shifting into high gear");
		shifters.shiftGear(Shifters.Speed.kHigh);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		autonomousCommand = oi.getAutonCommand();

		// start the robot out in low gear when starting autonomous
		shifters.shiftGear(Shifters.Speed.kLow);

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		// start robot in low gear when starting teleop
		shifters.shiftGear(Shifters.Speed.kLow);
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		synchronized(listener.cameraLock) {
			SmartDashboard.putNumber("TargetX", listener.targetX);
			SmartDashboard.putNumber("Height", listener.height);
			
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	} 
	
}
