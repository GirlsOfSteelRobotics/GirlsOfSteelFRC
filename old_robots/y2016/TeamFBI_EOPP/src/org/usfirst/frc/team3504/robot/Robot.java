package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team3504.robot.commands.AutoDrive;
import org.usfirst.frc.team3504.robot.commands.DriveCommand;
import org.usfirst.frc.team3504.robot.subsystems.Drive;
import org.usfirst.frc.team3504.robot.subsystems.Manipulator;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    //public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    private final OI m_oi;
    private final Drive m_drive;
    private final Manipulator m_manipulator;
    private final Shifters m_shifters;
    private final Command m_autonomousCommand;

    public Robot() {
        m_drive = new Drive();
        m_manipulator = new Manipulator();
        m_shifters = new Shifters();
        //autoChooser = new SendableChooser();
        //autoChooser.addDefault("Auto Drive", new AutoDrive());
        m_oi = new OI(m_shifters, m_manipulator);
        m_autonomousCommand = new AutoDrive(m_drive);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        m_drive.setDefaultCommand(new DriveCommand(m_oi, m_drive));
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        //autonomousCommand = (Command) autoChooser.getSelected();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
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
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }

}
