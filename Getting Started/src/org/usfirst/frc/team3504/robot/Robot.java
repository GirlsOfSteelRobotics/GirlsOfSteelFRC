package org.usfirst.frc.team3504.robot;

import com.ctre.phoenix.MotorControl.ControlMode;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	TalonSRX m_leftA = new TalonSRX(7);
	TalonSRX m_leftB = new TalonSRX(6);
	TalonSRX m_leftC = new TalonSRX(5);
	
	TalonSRX m_rightA = new TalonSRX(2);
	TalonSRX m_rightB = new TalonSRX(1);
	TalonSRX m_rightC = new TalonSRX(3);

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
		m_leftA.setInverted(true);
		m_leftB.set(ControlMode.Follower, m_leftA.getDeviceID());
		m_leftC.set(ControlMode.Follower, m_leftA.getDeviceID());
		m_rightB.set(ControlMode.Follower, m_rightA.getDeviceID());
		m_rightC.set(ControlMode.Follower, m_rightA.getDeviceID());
		myRobot = new DifferentialDrive(m_leftA.getWPILIB_SpeedController(), m_rightA.getWPILIB_SpeedController());
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
		//LiveWindow.run();
	}
	
	@Override
	public void disabledInit() {
		// Do nothing
	}
		
}
