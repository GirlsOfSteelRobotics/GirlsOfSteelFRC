package com.gos.deep_space;

import com.gos.deep_space.commands.ClimberHold;
import com.gos.deep_space.commands.PivotHold;
import com.gos.deep_space.subsystems.BabyDrive;
import com.gos.deep_space.subsystems.Blinkin;
import com.gos.deep_space.subsystems.Camera;
import com.gos.deep_space.subsystems.Chassis;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Collector;
import com.gos.deep_space.subsystems.Hatch;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private final Chassis m_chassis;
    private final Collector m_collector;
    private final Pivot m_pivot;
    private final Climber m_climber;
    private final BabyDrive m_babyDrive;
    private final Hatch m_hatch;
    private final Blinkin m_blinkin;
    private final Camera m_camera;
    //    private final GripPipelineListener m_listener;
    //    private final VisionThread m_visionThread; // NOPMD

    public Robot() {
        m_chassis = new Chassis();
        m_collector = new Collector();
        m_pivot = new Pivot();
        m_babyDrive = new BabyDrive();
        m_climber = new Climber();
        m_hatch = new Hatch();
        m_blinkin = new Blinkin();
        if (RobotBase.isReal()) {
            m_camera = new Camera();
        } else {
            m_camera = null;
        }
        // Create all subsystems BEFORE creating the Operator Interface (OI)
        new OI(m_chassis, m_babyDrive, m_blinkin, m_climber, m_collector, m_hatch, m_pivot);

        //listener = new GripPipelineListener();
        //visionThread = new VisionThread(camera.visionCam, new GripPipeline(), listener);
        //visionThread.start();

        System.out.println("Robot Init finished");

        m_climber.setDefaultCommand(new ClimberHold(m_climber));
        m_pivot.setDefaultCommand(new PivotHold(m_pivot));
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * This function is called once each time the robot enters Disabled mode. You
     * can use it to reset any subsystem information you want to clear when the
     * robot is disabled.
     */
    @Override
    public void disabledInit() {
        if (RobotBase.isReal()) {
            m_camera.closeMovieFile();
        }
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString code to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons to
     * the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        m_blinkin.setLightPattern(Blinkin.LightPattern.AUTO_DEFAULT);
        if (RobotBase.isReal()) {
            m_camera.openMovieFile();
        }
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
        m_blinkin.setLightPattern(Blinkin.LightPattern.TELEOP_DEFAULT);
        if (RobotBase.isReal()) {
            m_camera.openMovieFile();
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
        m_blinkin.setLightPattern(Blinkin.LightPattern.TELEOP_DEFAULT);
        double timeRemaining = DriverStation.getMatchTime();
        //System.out.println("Robot match time: " + DriverStation.getMatchTime());
        if (timeRemaining <= 30) {
            m_blinkin.setLightPattern(Blinkin.LightPattern.THIRTY_CLIMB);
        } else if (timeRemaining <= 40) {
            m_blinkin.setLightPattern(Blinkin.LightPattern.FORTY_CLIMB);
        }

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
