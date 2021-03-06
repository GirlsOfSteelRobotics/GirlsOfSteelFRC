package com.gos.preseason2016.team_squirtle.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.gos.preseason2016.team_squirtle.robot.commands.DriveByJoystick;
import com.gos.preseason2016.team_squirtle.robot.commands.autonomous.AutoDrive;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Camera;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Chassis;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Ramp;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Shifters;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    private final OI m_oi;
    private final Chassis m_chassis;
    private final Camera m_camera; // NOPMD
    private final Shifters m_shifters;
    private final Ramp m_ramp;

    private final Command m_autonomousCommand;

    public Robot() {
        m_chassis = new Chassis();
        m_camera = new Camera();
        m_shifters = new Shifters();
        m_ramp = new Ramp();
        // instantiate the command used for the autonomous period
        m_oi = new OI(m_shifters, m_ramp);
        m_autonomousCommand = new AutoDrive(m_chassis);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        m_chassis.setDefaultCommand(new DriveByJoystick(m_oi.getJoystick(), m_chassis));
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
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
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

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
