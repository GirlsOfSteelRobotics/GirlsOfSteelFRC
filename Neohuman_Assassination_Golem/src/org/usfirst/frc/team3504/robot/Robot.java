
package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.autonomous.*;
import org.usfirst.frc.team3504.robot.subsystems.*;

import edu.wpi.first.wpilibj.CameraServer;
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

	/* Declare variables for all subsystems and OI here, 
	 * but don't initialize them until robotInit() is called.
	 * (Initializing them here leads to very unclear error messages
	 * if any of them throw an exception.)
	 */
	public static OI oi;
	public static Chassis chassis;
	public static Shifters shifters;
	public static Flap flap;
	public static Claw claw;
	public static Pivot pivot;
	public static Camera camera;
	public static TestBoardPID testboard;
	public static LEDLights ledlights; 

    Command autonomousCommand;
    SendableChooser autoChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// Start by initializing each subsystem
    	chassis = new Chassis();
    	shifters = new Shifters();
    	flap = new Flap();
    	claw = new Claw();
    	pivot = new Pivot();
    	camera = new Camera();
    	testboard = new TestBoardPID();
    	ledlights = new LEDLights(); 
    	
    	// After all subsystems are set up, create the Operator Interface.
    	// If you call new OI() before the subsystems are created, it will fail.
		oi = new OI();

		// Populate the SmartDashboard menu for choosing the autonomous command to run
		autoChooser = new SendableChooser();
		autoChooser.addDefault("AutoDriveDistance", new AutoDriveDistance(110));
		autoChooser.addObject("AutoDriveSlowly", new AutoDriveSlowly(55));
		SmartDashboard.putData("Autochooser: ", autoChooser);
		
        
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	autonomousCommand = (Command) autoChooser.getSelected();
    	// schedule the autonomous command (example)
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
		
        // Start the robot out in low gear when changing from auto to tele-op
		shifters.shiftLeft(Shifters.Speed.kLow);
		shifters.shiftRight(Shifters.Speed.kLow);
		
		Robot.chassis.resetEncoderDistance();
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
