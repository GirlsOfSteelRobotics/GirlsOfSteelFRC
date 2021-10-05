/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.power_up;

import com.gos.power_up.commands.autonomous.AutoDriveToBaseline;
import com.gos.power_up.commands.autonomous.AutoFarScaleAbsolute;
import com.gos.power_up.commands.autonomous.AutoMiddleSwitch;
import com.gos.power_up.commands.autonomous.AutoNearScale;
import com.gos.power_up.commands.autonomous.AutoNearSwitch;
import com.gos.power_up.subsystems.Blobs;
import com.gos.power_up.subsystems.Camera;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Climber;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Shifters;
import com.gos.power_up.subsystems.Wrist;
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
    public static Climber climber;
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
        climber = new Climber();
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
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public void autonomousInit() {

        Robot.chassis.zeroSensors();
        gameData.reset();

        System.out.println("Starting Auto...");
        //Get robot side, switch side, scale side, element priority
        GameData.FieldSide robotSide = gameData.getRobotSide();
        GameData.FieldElement elementPriority = gameData.getElementPriority();
        GameData.FieldSide switchSide = gameData.getSwitchSide();
        GameData.FieldSide scaleSide = gameData.getScaleSide();
        boolean scaleOverride = gameData.getScaleOverride();

        if (gameData.getNoAuto()) {
            m_autonomousCommand = null;
        } else if (robotSide == GameData.FieldSide.left || robotSide == GameData.FieldSide.right) //if robot in the corner

        {
            Robot.shifters.shiftGear(Shifters.Speed.kHigh);

            if (elementPriority == GameData.FieldElement.Switch) //switch priority
            {
                if (switchSide == robotSide) {
                    m_autonomousCommand = new AutoNearSwitch(switchSide);
                } else if (scaleSide == robotSide) {
                    m_autonomousCommand = new AutoNearScale(scaleSide);
                } else if (scaleOverride) {
                    m_autonomousCommand = new AutoFarScaleAbsolute(scaleSide);
                } else {
                    m_autonomousCommand = new AutoDriveToBaseline();
                }
            } else //scale priority
            {
                if (scaleSide == robotSide) {
                    m_autonomousCommand = new AutoNearScale(scaleSide);
                } else if (scaleOverride) {
                    m_autonomousCommand = new AutoFarScaleAbsolute(scaleSide);
                } else if (switchSide == robotSide) {
                    m_autonomousCommand = new AutoNearSwitch(switchSide);
                } else {
                    m_autonomousCommand = new AutoDriveToBaseline();
                }
            }
        } else if (robotSide == GameData.FieldSide.middle) {
            Robot.shifters.shiftGear(Shifters.Speed.kLow);
            if (switchSide != GameData.FieldSide.bad) {
                m_autonomousCommand = new AutoMiddleSwitch(switchSide);
            } else {
                m_autonomousCommand = new AutoDriveToBaseline();
            }
        } else {
            System.out.println("AutoInit: Robot field side from DIO ports invalid!!");
            m_autonomousCommand = new AutoDriveToBaseline();
        }

        System.out.println("Auto: " + m_autonomousCommand);

        //Other auto commands for testing:
        //m_autonomousCommand = new AutoDriveToBaseline();

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
