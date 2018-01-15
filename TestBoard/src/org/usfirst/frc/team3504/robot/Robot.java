/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1736.lib.Sensors.PulsedLightLIDAR;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private WPI_TalonSRX motor1;
	private WPI_TalonSRX motor2;
	private WPI_TalonSRX motor3;
	private SpeedControllerGroup leftGroup;
	private DifferentialDrive m_robotDrive;
	private Joystick m_stick;
	private Timer m_timer;
	private PulsedLightLIDAR lidar;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		motor1 = new WPI_TalonSRX(1);
		motor2 = new WPI_TalonSRX(2);
		motor3 = new WPI_TalonSRX(3);
		leftGroup = new SpeedControllerGroup(motor1, motor2);
		m_robotDrive = new DifferentialDrive(leftGroup, motor3);
		m_stick = new Joystick(0);
		m_timer = new Timer();
		lidar = new PulsedLightLIDAR();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (m_timer.get() < 2.0) {
			m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			m_robotDrive.stopMotor(); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		lidar.update();
		double dist = lidar.getDistance();
		System.out.printf("LIDAR distance: %f\n", dist);
		SmartDashboard.putNumber("LIDAR", dist);
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("getY", m_stick.getY());
		m_robotDrive.stopMotor();
		if (m_stick.getTrigger()) {
			motor3.set(m_stick.getY());
		} else {
			motor3.stopMotor();
		}
	}
	
	@Override
	public void disabledInit() {
		//lidar.stop();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
