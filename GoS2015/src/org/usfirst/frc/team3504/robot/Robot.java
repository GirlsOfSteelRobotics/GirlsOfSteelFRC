
package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveBackwards;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveForward;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveLeft;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveRight;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoOneTote;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoPlow;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoToteAndContainer;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnClockwise;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoTurnCounterClockwise;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoUltrasonic;
import org.usfirst.frc.team3504.robot.commands.autonomous.Lifting;
import org.usfirst.frc.team3504.robot.commands.autonomous.Release;
import org.usfirst.frc.team3504.robot.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Chassis chassis;
	public static Camera camera;
	public static Collector collector;
	public static Fingers fingers;
	public static Lifter forklift;
	public static Doors doors;
	public static UltrasonicSensor ultrasonicsensor; 
	public static OI oi;
	public static PhotoSensor photosensor;
	public static Shack shack; 
    Command autonomousCommand;
    SendableChooser autoChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		chassis = new Chassis();
		camera = new Camera();
		collector = new Collector();
		fingers = new Fingers();
		forklift = new Lifter();
		doors = new Doors();
		ultrasonicsensor = new UltrasonicSensor();
		photosensor = new PhotoSensor();
		shack = new Shack(); 
		Robot.chassis.resetGyro();
        oi = new OI();
        autoChooser = new SendableChooser();
        autoChooser.addDefault("AutoDriveRight", new AutoDriveRight(150));
        autoChooser.addObject("AutoDriveLeft", new AutoDriveLeft(150));
        autoChooser.addObject("AutoDriveForward", new AutoDriveForward(50));
        autoChooser.addObject("AutoDriveBackwards", new AutoDriveBackwards(50));
        autoChooser.addObject("AutoOneTote", new AutoOneTote());
        autoChooser.addObject("AutoTurnClockwise", new AutoTurnClockwise());
        autoChooser.addObject("AutoTurnCounterClockwise", new AutoTurnCounterClockwise());
        autoChooser.addObject("AutoPlow", new AutoPlow());
        autoChooser.addObject("AutoToteAndContainer", new AutoToteAndContainer());
        autoChooser.addObject("AutoUltrasonic", new AutoUltrasonic());
        autoChooser.addObject("Lifting", new Lifting());
        autoChooser.addObject("Release", new Release());
        
        
        SmartDashboard.putData("Auto mode", autoChooser);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}
