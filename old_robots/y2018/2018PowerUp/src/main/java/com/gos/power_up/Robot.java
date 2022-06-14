/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.power_up;

import com.gos.power_up.commands.CollectorHold;
import com.gos.power_up.commands.LiftHold;
import com.gos.power_up.commands.WristHold;
import com.gos.power_up.commands.autonomous.AutoDriveToBaseline;
import com.gos.power_up.commands.autonomous.AutoFarScaleAbsolute;
import com.gos.power_up.commands.autonomous.AutoMiddleSwitch;
import com.gos.power_up.commands.autonomous.AutoNearScale;
import com.gos.power_up.commands.autonomous.AutoNearSwitch;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Climber;
import com.gos.power_up.subsystems.Collector;
import com.gos.power_up.subsystems.Lift;
import com.gos.power_up.subsystems.Shifters;
import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final Lift m_lift;
    private final Wrist m_wrist;
    private final Collector m_collector;
    private final Climber m_climber;
    private final GameData m_gameData;

    private Command m_autonomousCommand;

    public Robot() {
        m_collector = new Collector();
        m_chassis = new Chassis();
        m_shifters = new Shifters();
        m_lift = new Lift();
        m_wrist = new Wrist();
        m_climber = new Climber();
        m_gameData = new GameData();
        new OI(m_chassis, m_shifters, m_lift, m_wrist, m_climber, m_collector);

        m_lift.setDefaultCommand(new LiftHold(m_lift));
        m_wrist.setDefaultCommand(new WristHold(m_wrist));
        m_collector.setDefaultCommand(new CollectorHold(m_collector));
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
        CommandScheduler.getInstance().run();
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
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.CognitiveComplexity"})
    public void autonomousInit() {

        m_chassis.zeroSensors();
        m_gameData.reset();

        System.out.println("Starting Auto...");
        //Get robot side, switch side, scale side, element priority
        GameData.FieldSide robotSide = m_gameData.getRobotSide();
        GameData.FieldElement elementPriority = m_gameData.getElementPriority();
        GameData.FieldSide switchSide = m_gameData.getSwitchSide();
        GameData.FieldSide scaleSide = m_gameData.getScaleSide();
        boolean scaleOverride = m_gameData.getScaleOverride();

        if (m_gameData.getNoAuto()) {
            m_autonomousCommand = null; // NOPMD
        } else if (robotSide == GameData.FieldSide.LEFT || robotSide == GameData.FieldSide.RIGHT) { //if robot in the corner
            m_shifters.shiftGear(Shifters.Speed.HIGH);

            if (elementPriority == GameData.FieldElement.SWITCH) { //switch priority
                if (switchSide == robotSide) {
                    m_autonomousCommand = new AutoNearSwitch(m_chassis, m_shifters, m_lift, m_wrist, m_collector, switchSide);
                } else if (scaleSide == robotSide) {
                    m_autonomousCommand = new AutoNearScale(m_chassis, m_shifters, m_lift, m_wrist, m_collector, scaleSide);
                } else if (scaleOverride) {
                    m_autonomousCommand = new AutoFarScaleAbsolute(m_chassis, m_lift, m_wrist, m_collector, scaleSide);
                } else {
                    m_autonomousCommand = new AutoDriveToBaseline(m_chassis);
                }
            } else { //scale priority
                if (scaleSide == robotSide) {
                    m_autonomousCommand = new AutoNearScale(m_chassis, m_shifters, m_lift, m_wrist, m_collector, scaleSide);
                } else if (scaleOverride) {
                    m_autonomousCommand = new AutoFarScaleAbsolute(m_chassis, m_lift, m_wrist, m_collector, scaleSide);
                } else if (switchSide == robotSide) {
                    m_autonomousCommand = new AutoNearSwitch(m_chassis, m_shifters, m_lift, m_wrist, m_collector, switchSide);
                } else {
                    m_autonomousCommand = new AutoDriveToBaseline(m_chassis);
                }
            }
        } else if (robotSide == GameData.FieldSide.MIDDLE) {
            m_shifters.shiftGear(Shifters.Speed.LOW);
            if (switchSide != GameData.FieldSide.BAD) {
                m_autonomousCommand = new AutoMiddleSwitch(m_chassis, m_lift, m_wrist, m_collector, switchSide);
            } else {
                m_autonomousCommand = new AutoDriveToBaseline(m_chassis);
            }
        } else {
            System.out.println("AutoInit: Robot field side from DIO ports invalid!!");
            m_autonomousCommand = new AutoDriveToBaseline(m_chassis);
        }

        System.out.println("Auto: " + m_autonomousCommand);

        //Other auto commands for testing:
        //m_autonomousCommand = new AutoDriveToBaseline();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

        System.out.println("Hopefully auto is runnin");
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
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
        m_lift.setGoalLiftPosition(m_lift.getLiftPosition());
        m_wrist.setGoalWristPosition(m_wrist.getWristPosition());
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
