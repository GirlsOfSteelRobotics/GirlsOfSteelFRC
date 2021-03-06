package com.gos.preseason2017.team2.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.preseason2017.team2.robot.commands.DriveByJoystick;
import com.gos.preseason2017.team2.robot.commands.autonomous.AutoDoNothing;
import com.gos.preseason2017.team2.robot.commands.autonomous.AutoDriveBackwards;
import com.gos.preseason2017.team2.robot.commands.autonomous.AutoDriveForward;
import com.gos.preseason2017.team2.robot.subsystems.Chassis;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends TimedRobot {
    private final Chassis m_chassis;
    private final Manipulator m_manipulator;
    private final OI m_oi;
    private final SendableChooser<Command> m_chooser;
    private Command m_autonomousCommand;

    public Robot() {
        m_chassis = new Chassis();
        m_manipulator = new Manipulator();

        //Initialize OI after all subsystems are initialized
        m_oi = new OI(m_manipulator);

        m_chooser = new SendableChooser<>();
        m_chooser.setDefaultOption("Default: Do Nothing", new AutoDoNothing(m_chassis));
        m_chooser.addOption("Drive Forwards(10 in, 0.5 speed)", new AutoDriveForward(m_chassis, 10.0, 0.5));
        m_chooser.addOption("Drive Backwards(10 in, 0.5 speed)", new AutoDriveBackwards(m_chassis, 10.0, 0.5));
        SmartDashboard.putData("Auto mode", m_chooser);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // Set the default command for a subsystem here.
        m_chassis.setDefaultCommand(new DriveByJoystick(m_oi.getStick(), m_chassis));
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
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
     * below the Gyro
     * <p>
     * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
     * or additional comparisons to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_chooser.getSelected();

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
        m_chassis.ahrsToSmartDashboard();

        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }
}
