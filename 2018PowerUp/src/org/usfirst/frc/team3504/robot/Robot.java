/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.GameData.FieldElement;
import org.usfirst.frc.team3504.robot.GameData.FieldSide;
import org.usfirst.frc.team3504.robot.commands.*;
import org.usfirst.frc.team3504.robot.commands.autonomous.*;
import org.usfirst.frc.team3504.robot.subsystems.Blobs;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lift;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static Chassis chassis;
	public static Shifters shifters;
	public static Lift lift;
	public static Wrist wrist;
	public static Collector collector;
	public static Blobs blobs;
	public static Camera camera;
	public static OI oi;
	public static GameData gameData;

	Command m_autonomousCommand;
	public static String motionProfile = "91-small";

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		collector = new Collector();
		chassis = new Chassis();
		shifters = new Shifters();
		lift = new Lift();
		wrist = new Wrist();
		blobs = new Blobs();
		camera = new Camera();
		oi = new OI();
		gameData = new GameData();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
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
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		Robot.shifters.shiftGear(Shifters.Speed.kHigh);
		Robot.chassis.zeroSensors();

		
		System.out.println("Starting Auto...");
		//Get robot side, switch side, scale side, element priority
		FieldSide robotSide = gameData.getRobotSide();
		FieldElement elementPriority = gameData.getElementPriority();
		FieldSide switchSide = gameData.getSwitchSide(); 
		FieldSide scaleSide = gameData.getScaleSide(); 
		
//		if(gameData.getNoAuto())
//		{
//			m_autonomousCommand = null;
//		}
//		else if(robotSide == FieldSide.left || robotSide == FieldSide.right) //if robot in the corner
//		{
//			if (elementPriority == FieldElement.Switch) //switch priority
//			{
//				if (switchSide == robotSide) m_autonomousCommand = new AutoNearSwitch(switchSide);
//				else if (scaleSide == robotSide) m_autonomousCommand = new AutoNearScale(scaleSide);
//				else m_autonomousCommand = new AutoDriveToBaseline();
//			}
//			else //scale priority
//			{
//				if (scaleSide == robotSide) m_autonomousCommand = new AutoNearScaleAngled(scaleSide);
//				else if (switchSide == robotSide) m_autonomousCommand = new AutoNearSwitch(switchSide);
//				else m_autonomousCommand = new AutoDriveToBaseline();
//			}
//		}
//		else if(robotSide == FieldSide.middle)
//		{
//			if (switchSide != FieldSide.bad) m_autonomousCommand = new AutoMiddleSwitch(switchSide);
//			else m_autonomousCommand = new AutoDriveToBaseline();
//		}
//		else 
//		{
//			System.out.println("AutoInit: Robot field side from DIO ports invalid!!");
//			m_autonomousCommand = new AutoDriveToBaseline();
//		}
//		
//		System.out.println("Auto: " + m_autonomousCommand);
//		
		/*
		boolean logicPriority = false; //get from DIO port; true = field element, false = stay on our side
		FieldElement elementPriority = FieldElement.Switch;
		FieldElement crossPriority = FieldElement.Scale;
		
		if (switchSide == FieldSide.bad || scaleSide == FieldSide.bad) m_autonomousCommand = new AutoBaseLine();
		else if (logicPriority) //Field Element Priority
		{
			if (elementPriority == FieldElement.Switch)
			{
				if (switchSide == robotSide) m_autonomousCommand = new AutoNearSwitch(robotSide);
				else m_autonomousCommand = new AutoFarSwitch(robotSide);
			}
			else //if (elementPriority == FieldElement.Scale)
			{
				if (scaleSide == robotSide) m_autonomousCommand = new AutoNearScale(robotSide);
				else m_autonomousCommand = new AutoFarScale(robotSide);
			}
		}
		else //Stay on our side priority
		{
			if (elementPriority == FieldElement.Switch)
			{
				if (switchSide == robotSide) m_autonomousCommand = new AutoNearSwitch(robotSide);
				else if (scaleSide == robotSide) m_autonomousCommand = new AutoNearScale(robotSide);
				else
				{
					if (crossPriority == FieldElement.Switch) m_autonomousCommand = new AutoFarSwitch(robotSide);
					else m_autonomousCommand = new AutoFarScale(robotSide);
				}
			}
			else //if (elementPriority == FieldElement.Scale)
			{
				if (switchSide == robotSide) m_autonomousCommand = new AutoNearScale(robotSide);
				else if (scaleSide == robotSide) m_autonomousCommand = new AutoNearSwitch(robotSide);
				else
				{
					if (crossPriority == FieldElement.Switch) m_autonomousCommand = new AutoFarSwitch(robotSide);
					else m_autonomousCommand = new AutoFarScale(robotSide);
				}
			}
		}
		*/
		
		//Other auto commands for testing:
		//m_autonomousCommand = new AutoPrintData();
		//m_autonomousCommand = new AutoSwitchSimple();
		//m_autonomousCommand = new DriveByMotionProfile("/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat");
		//m_autonomousCommand = new DriveByDistance(100, Shifters.Speed.kLow);
		//m_autonomousCommand = new AutoSwitchSimple();
		//m_autonomousCommand = new AutoNearScaleAngled(FieldSide.left);
		//m_autonomousCommand = new TurnByMotionMagic(-90);
		//m_autonomousCommand = new AutoNearScale(scaleSide);
		
		//Motion Magic Testing
		//m_autonomousCommand = new DriveByMotionMagic(166.0,0);
		//m_autonomousCommand = new AutoTurnRight();
		//m_autonomousCommand = new AutoTurnLeft();
		//m_autonomousCommand = new AutoMiddleSwitch(FieldSide.left);

		m_autonomousCommand = new TurnInPlace(90);
		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		System.out.println("Hopefully auto is runnin");
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		lift.setGoalLiftPosition(lift.getLiftPosition());
		wrist.setGoalWristPosition(wrist.getWristPosition());
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
