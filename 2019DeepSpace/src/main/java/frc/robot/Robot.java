package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Collector; 
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Climber; 
import frc.robot.subsystems.BabyDrive; 
import frc.robot.subsystems.Hatch;
import frc.robot.subsystems.ScrewClimber;

public class Robot extends TimedRobot {
  public static Chassis chassis;
  public static Collector collector; 
  public static Wrist wrist;
  public static Climber climber; 
  public static ScrewClimber screwClimber;
  public static BabyDrive babyDrive;
  public static Hatch hatch;
  public static OI oi;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    chassis = new Chassis();
    collector = new Collector();  
    wrist = new Wrist();
    climber = new Climber(); 
    babyDrive = new BabyDrive(); 
    screwClimber = new ScrewClimber();
    oi = new OI();
    hatch = new Hatch();
    System.out.println("Robot Init");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
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
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
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
