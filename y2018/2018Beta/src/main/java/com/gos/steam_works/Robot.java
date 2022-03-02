package com.gos.steam_works;

import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.vision.VisionThread;


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
    private final Shifters m_shifters;
    private final Agitator m_agitator;
    private final Shooter m_shooter;
    private final Camera m_camera;
    private final Loader m_loader;
    private final GripPipelineListener m_listener;

    private Command m_autonomousCommand;

    private final VisionThread m_visionThread; // NOPMD

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public Robot() {
        m_chassis = new Chassis();
        m_shifters = new Shifters();
        m_agitator = new Agitator();
        m_shooter = new Shooter();
        m_camera = new Camera();
        m_loader = new Loader();
        m_listener = new GripPipelineListener();

        // Initialize all subsystems before creating the OI
        m_oi = new OI(m_chassis, m_shifters, m_shooter, m_loader, m_agitator, m_listener);

        /*
        try {
            @SuppressWarnings("unused")
            Process p;
            p = new ProcessBuilder("chmod", "+x", "/home/lvuser/GRIPonRoboRIO")
                    .start();
            p = new ProcessBuilder("/home/lvuser/GRIPonRoboRIO", "-f", "20",
                    "-e", "8").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        m_visionThread = new VisionThread(m_camera.getVisionCamera(), new GripPipeline(), m_listener); // NOPMD
        m_visionThread.start();

    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        System.out.println("DisabledInit shifting into high gear");
        m_shifters.shiftGear(Shifters.Speed.kHigh);
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
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_oi.getAutonCommand();

        // start the robot out in low gear when starting autonomous
        m_shifters.shiftGear(Shifters.Speed.kLow);

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

        // start robot in low gear when starting teleop
        m_shifters.shiftGear(Shifters.Speed.kLow);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
        synchronized (GripPipelineListener.cameraLock) {
            SmartDashboard.putNumber("TargetX", m_listener.targetX);
            SmartDashboard.putNumber("Height", m_listener.height);

        }
    }

}
