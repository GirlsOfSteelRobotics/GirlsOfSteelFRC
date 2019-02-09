package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.*;

import edu.wpi.first.vision.VisionThread;

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
  public static OI oi;

  private VisionThread visionThread;

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
    listener = new GripPipelineListener();
    // camera = new Camera();
    // visionThread = new VisionThread(camera.visionCam, new GripPipeline(),
    // listener);
    oi = new OI();
    System.out.println("Robot Init");

    // visionThread.start();
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

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
}
