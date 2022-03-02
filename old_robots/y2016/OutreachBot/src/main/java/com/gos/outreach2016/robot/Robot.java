package com.gos.outreach2016.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.outreach2016.robot.commands.AutonomousCommand;
import com.gos.outreach2016.robot.commands.DriveByJoystick;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors;
import com.gos.outreach2016.robot.subsystems.DriveSystem;
import com.gos.outreach2016.robot.subsystems.Manipulator;
import com.gos.outreach2016.robot.subsystems.Shifters;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    // Declare and initialize each subsystem
    // The constructor will create motor and sensor objects and do other setup
    private final DriveSystem m_driveSystem;
    private final AccessoryMotors m_accessoryMotors;
    private final Shifters m_shifters;
    private final Manipulator m_manipulator;
    private final OI m_oi;

    private Command m_autonomousCommand;
    private final SendableChooser m_chooser;

    public Robot() {
        m_driveSystem = new DriveSystem();
        m_accessoryMotors = new AccessoryMotors();
        m_shifters = new Shifters();
        m_manipulator = new Manipulator();
        m_oi = new OI(m_shifters, m_driveSystem, m_manipulator, m_accessoryMotors);

        // Allow the driver to choose the autonomous command from a SmartDashboard menu
        m_chooser = new SendableChooser();
        m_chooser.addDefault("Default Auto", new AutonomousCommand(m_driveSystem, m_accessoryMotors));
        // chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", m_chooser);
        SmartDashboard.putData(new DriveByJoystick(m_oi, m_driveSystem));
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        m_driveSystem.setDefaultCommand(new DriveByJoystick(m_oi, m_driveSystem));
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
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard.
     * <p>
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = (Command) m_chooser.getSelected();

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    /**
     * This function is called periodically during autonomous
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
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }
}
