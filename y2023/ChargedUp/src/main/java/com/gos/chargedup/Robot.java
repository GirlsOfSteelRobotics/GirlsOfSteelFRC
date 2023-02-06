// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.chargedup;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.littletonrobotics.frc2023.util.Alert;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    private final PneumaticHub m_pneumaticHub = new PneumaticHub();

    private final Alert m_rightClawPistonAlert = new Alert("Claw", "Right Solenoid Error", Alert.AlertType.ERROR);

    private final Alert m_leftClawPistonAlert = new Alert("Claw", "Left Solenoid Error", Alert.AlertType.ERROR);

    private final Alert m_rightIntakePistonAlert = new Alert("Intake", "Right Intake Piston Error", Alert.AlertType.ERROR);

    private final Alert m_leftIntakePistonAlert = new Alert("Intake", "Left Intake Piston Error", Alert.AlertType.ERROR);

    private final Alert m_innerArmAlert = new Alert("Arm", "Inner Arm Error", Alert.AlertType.ERROR);

    private final Alert m_outerArmAlert = new Alert("Arm", "Outer Arm Error", Alert.AlertType.ERROR);

    private PneumaticHub m_pneumaticHub;


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        m_pneumaticHub = new PneumaticHub();
        m_robotContainer = new RobotContainer(m_pneumaticHub);
    }


    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
        m_leftClawPistonAlert.set(m_pneumaticHub.getFaults().Channel1Fault);

        m_rightClawPistonAlert.set(m_pneumaticHub.getFaults().Channel0Fault);

        m_leftIntakePistonAlert.set(m_pneumaticHub.getFaults().Channel3Fault);

        m_rightIntakePistonAlert.set(m_pneumaticHub.getFaults().Channel5Fault);

        m_innerArmAlert.set(m_pneumaticHub.getFaults().Channel2Fault);

        m_outerArmAlert.set(m_pneumaticHub.getFaults().Channel7Fault);

    }


    /**
     * This method is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }


    @Override
    public void disabledPeriodic() {
    }


    /**
     * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
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
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }


    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
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
