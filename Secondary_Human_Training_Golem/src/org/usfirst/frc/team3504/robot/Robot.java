package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.autonomous.*;
import org.usfirst.frc.team3504.robot.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Climb;
import org.usfirst.frc.team3504.robot.subsystems.GearCover;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoBlueHopper;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDriveForward;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoRedHopper;
//import com.mindsensors.CANLight;
import org.usfirst.frc.team3504.robot.subsystems.*;

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
	
	public static OI oi;
	public static Chassis chassis;
	public static Shifters shifters;
	public static GearCover gearCover;
	public static Climb climb; 
	public static Shooter shooter; 
	public static Camera camera;

    Command autonomousCommand;
    SendableChooser<Command> chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		chassis = new Chassis();
		shifters = new Shifters();
		gearCover = new GearCover();
		climb = new Climb();
		shooter = new Shooter();
		camera = new Camera();

		// Initialize all subsystems before creating the OI
		oi = new OI();

		chooser = new SendableChooser<Command>();
        chooser.addDefault("Do Nothing", new AutoDoNothing());
        chooser.addObject("Base Line", new AutoDriveForward(10.0, 0.5)); //TODO: change value
        chooser.addObject("Blue Alliance Hopper", new AutoBlueHopper()); //TODO: change name
        chooser.addObject("Red Alliance Hopper", new AutoRedHopper()); //TODO: change name
        SmartDashboard.putData("Auto mode", chooser);
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
        autonomousCommand = (Command) chooser.getSelected();
    	
        //start the robot out in low gear when starting autonomous
        shifters.shiftGear(Shifters.Speed.kLow);
        
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
        
        //start robot in low gear when starting teleop
        shifters.shiftGear(Shifters.Speed.kLow);
        
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
