// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.rebuilt;

import com.gos.lib.alerts.PowerDistributionAlerts;
import org.wpilib.system.DataLogManager;
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.driverstation.RobotState;
import org.wpilib.hardware.power.PowerDistribution;
import org.wpilib.framework.TimedRobot;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.command2.Command;
import org.wpilib.command2.CommandScheduler;

import java.util.List;

import static com.gos.rebuilt.MatchTime.shouldIShoot;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;

    private final PowerDistribution m_pdh;
    private final PowerDistributionAlerts m_pdhAlert;


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public Robot() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        m_pdh = new PowerDistribution(CANPort.CAN_S0);
        m_robotContainer = new RobotContainer(m_pdh);
        m_pdhAlert = new PowerDistributionAlerts(m_pdh, List.of(17, 18, 19));
    }


    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before telemetry and tunables
     * are updated.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
        Telemetry.log("should I shoot right now??", shouldIShoot());
        m_pdhAlert.checkAlerts();
    }


    /**
     * This method is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }


    @Override
    public void disabledPeriodic() {
        if (RobotState.isFMSAttached()) {
            DataLogManager.start();
        }

        m_robotContainer.disabledPeriodic();
    }


    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }


    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
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
    }


    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }


    @Override
    public void utilityInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }


    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void utilityPeriodic() {
    }


    /**
     * This method is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }


    /**
     * This method is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
