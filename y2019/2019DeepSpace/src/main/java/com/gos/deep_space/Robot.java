package com.gos.deep_space;

import com.gos.deep_space.subsystems.BabyDrive;
import com.gos.deep_space.subsystems.Blinkin;
import com.gos.deep_space.subsystems.Camera;
import com.gos.deep_space.subsystems.Chassis;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Collector;
import com.gos.deep_space.subsystems.Hatch;
import com.gos.deep_space.subsystems.Lidar;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
    public static Chassis chassis;
    public static Collector collector;
    public static Pivot pivot;
    public static Climber climber;
    public static BabyDrive babyDrive;
    public static Hatch hatch;
    public static Blinkin blinkin;
    public static GripPipelineListener listener;
    public static Camera camera;
    public static Lidar lidar;
    public static OI oi;
    private VisionThread visionThread; // NOPMD

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        chassis = new Chassis();
        collector = new Collector();
        pivot = new Pivot();
        babyDrive = new BabyDrive();
        climber = new Climber();
        hatch = new Hatch();
        blinkin = new Blinkin();
        if (RobotBase.isReal()) {
            camera = new Camera();
        }
        lidar = new Lidar();
        // Create all subsystems BEFORE creating the Operator Interface (OI)
        oi = new OI();

        //listener = new GripPipelineListener();
        //visionThread = new VisionThread(camera.visionCam, new GripPipeline(), listener);
        //visionThread.start();

        System.out.println("Robot Init finished");
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
            camera.closeMovieFile();
        }
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
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
        Robot.blinkin.setLightPattern(Blinkin.LightPattern.AUTO_DEFAULT);
        if (RobotBase.isReal()) {
            camera.openMovieFile();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        Robot.blinkin.setLightPattern(Blinkin.LightPattern.TELEOP_DEFAULT);
        if (RobotBase.isReal()) {
            camera.openMovieFile();
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        Robot.blinkin.setLightPattern(Blinkin.LightPattern.TELEOP_DEFAULT);
        double timeRemaining = DriverStation.getInstance().getMatchTime();
        //System.out.println("Robot match time: " + DriverStation.getInstance().getMatchTime());
        if (timeRemaining <= 30) {
            Robot.blinkin.setLightPattern(Blinkin.LightPattern.THIRTY_CLIMB);
        } else if (timeRemaining <= 40) {
            Robot.blinkin.setLightPattern(Blinkin.LightPattern.FORTY_CLIMB);
        }

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }
}
