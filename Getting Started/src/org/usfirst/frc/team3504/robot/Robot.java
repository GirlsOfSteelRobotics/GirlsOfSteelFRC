package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	CANTalon m_leftA = new CANTalon(1);
	CANTalon m_leftB = new CANTalon(2);
	CANTalon m_leftC = new CANTalon(3);
	
	CANTalon m_rightA = new CANTalon(4);
	CANTalon m_rightB = new CANTalon(5);
	CANTalon m_rightC = new CANTalon(6);
	
	DifferentialDrive myRobot;
	
	Joystick stick;
	Timer timer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		/* Make B and C motors follow instructions given to A motors */
		m_leftB.changeControlMode(CANTalon.TalonControlMode.Follower);
		m_leftC.changeControlMode(CANTalon.TalonControlMode.Follower);
		m_rightB.changeControlMode(CANTalon.TalonControlMode.Follower);
		m_rightC.changeControlMode(CANTalon.TalonControlMode.Follower);
		m_leftB.set(m_leftA.getDeviceID());
		m_leftC.set(m_leftA.getDeviceID());
		m_rightB.set(m_rightA.getDeviceID());
		m_rightC.set(m_rightA.getDeviceID());

		myRobot = new DifferentialDrive(m_leftA, m_rightA);
		stick = new Joystick(0);
		timer = new Timer();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (timer.get() < 2.0) {
			myRobot.tankDrive(0.5, 0.5); // drive forwards half speed
		} else {
			myRobot.tankDrive(0.0, 0.0); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		myRobot.curvatureDrive(stick.getX(), stick.getY(), true);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
