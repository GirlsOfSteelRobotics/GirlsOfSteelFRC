/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoBaseLine;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveToBaseline;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoFarScale;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoFarSwitch;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoNearScale;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoNearSwitch;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoPrintData;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoSwitchSimple;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnRight;
import org.usfirst.frc.team3504.robot.subsystems.Blobs;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lift;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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
	public static enum FieldSide {
		left, right, middle, bad
	}
	public static enum FieldElement {
		Switch, Scale
	}

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
		
		lift.setGoalLiftPosition(lift.getLiftPosition());
		wrist.setGoalWristPosition(wrist.getWristPosition());
		
		//Determine which side of the field out robot is on
		FieldSide robotSide;
		if(!RobotMap.dio1.get()) robotSide = FieldSide.left; //get from DIO port
		else if (!RobotMap.dio2.get()) robotSide = FieldSide.middle;
		else if (!RobotMap.dio3.get()) robotSide = FieldSide.right;
		else robotSide = FieldSide.bad;
		
		FieldSide switchSide = getSwitchSide();
		FieldSide scaleSide = getScaleSide(); 
		
		if(!RobotMap.dio4.get())
		{
			m_autonomousCommand = null;
		}
		else if(robotSide == FieldSide.left)
		{
			if (switchSide == FieldSide.left) m_autonomousCommand = new AutoNearSwitch(FieldSide.left);
			else m_autonomousCommand = new AutoDriveToBaseline();
		}
		else if(robotSide == FieldSide.right) 
		{
			if (switchSide == FieldSide.right) m_autonomousCommand = new AutoNearSwitch(FieldSide.right);
			else m_autonomousCommand = new AutoDriveToBaseline();
		}
		else if(robotSide == FieldSide.middle)
		{
			if (switchSide == FieldSide.right) m_autonomousCommand = new AutoSwitchSimple();
			else m_autonomousCommand = new AutoDriveToBaseline();
		}
		else 
		{
			System.out.println("Robot field side from DIO ports invalid!!");
			m_autonomousCommand = new AutoDriveToBaseline();
		}
		
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
		m_autonomousCommand = new AutoNearScale(FieldSide.left);
		
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		//Motion Magic Testing
		//m_autonomousCommand = new DriveByMotionMagic(166.0,0);
		//m_autonomousCommand = new AutoTurnRight();
		//m_autonomousCommand = new AutoTurnLeft();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
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
	
	public static FieldSide getSwitchSide() //TODO: test
	{
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		int tim = 0;
		
		while(tim <= 5 && (gameData == null || gameData.equals(""))) 
		{
			Timer.delay(0.2);
			tim++;
		}
		
		if (gameData == null || gameData.equals("")) return FieldSide.bad;
		
		if(gameData.charAt(0) == 'L')
		{
			return FieldSide.left;
		} 
		else 
		{
			return FieldSide.right;
		}
	}
	
	public static FieldSide getScaleSide() //TODO: test
	{
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		int tim = 0;
		
		while(tim <= 5 && (gameData == null || gameData.equals(""))) 
		{
			Timer.delay(0.2);
			tim++;
		}
		
		if (gameData == null || gameData.equals("")) return FieldSide.bad;
		
		if(gameData.charAt(1) == 'L')
		{
			return FieldSide.left;
		} 
		else 
		{
			return FieldSide.right;
		}
	}
	
}
