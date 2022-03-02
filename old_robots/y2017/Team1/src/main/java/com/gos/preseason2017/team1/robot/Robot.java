package com.gos.preseason2017.team1.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.preseason2017.team1.robot.commands.DriveByJoystick;
import com.gos.preseason2017.team1.robot.commands.autonomous.AutoDoNothing;
import com.gos.preseason2017.team1.robot.commands.autonomous.AutoDriveForwards;
import com.gos.preseason2017.team1.robot.subsystems.DriveSystem;
import com.gos.preseason2017.team1.robot.subsystems.JawPiston;
import com.gos.preseason2017.team1.robot.subsystems.Shifters;
import com.gos.preseason2017.team1.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    private final DriveSystem m_driveSystem;
    private final Shifters m_shifters;
    private final JawPiston m_jaw;
    private final Shooter m_shooter;
    private final OI m_oi;

    private Command m_autonomousCommand;
    private final SendableChooser<Command> m_chooser;

    public Robot() {
        m_shifters = new Shifters();
        m_driveSystem = new DriveSystem(m_shifters);
        m_jaw = new JawPiston();
        m_shooter = new Shooter();

        //all subsystems must be initialized before creating OI
        m_oi = new OI(m_shifters, m_jaw, m_shooter);

        m_chooser = new SendableChooser<>();
        m_chooser.addDefault("Default: Do Nothing", new AutoDoNothing(m_driveSystem));
        m_chooser.addObject("Drive Forwards(dist=10,speed=0.5)", new AutoDriveForwards(m_driveSystem, 10.0, 0.5));
        SmartDashboard.putData("Auto mode", m_chooser);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // Default commands
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
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }
}
