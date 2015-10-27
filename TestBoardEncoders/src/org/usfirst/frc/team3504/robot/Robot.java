
package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3504.robot.commands.driveBackward;
import org.usfirst.frc.team3504.robot.commands.driveForward;
import org.usfirst.frc.team3504.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static drivetrain DriveTrain; 
	public static OI oi;
	Command autonomous;
	

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	

    
    public void robotInit() {
    	DriveTrain = new drivetrain();
		oi = new OI();
		

		
        // instantiate the command used for the autonomous period
        //autonomousCommand = new driveForward();

//		SmartDashboard.putData("Drive Forward", new driveForward());
//		SmartDashboard.putData("Drive Backward", new driveBackward());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomous != null) 
        	{
        	autonomous.start();
        	}
    }
    
    //This function is called periodically during autonomous
     
    public void autonomousPeriodic() {
    	Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
       if (autonomous != null) 
    	   {
    	   autonomous.cancel();
    	   }
    }

     //This function is called when the disabled button is hit.
     //You can use it to reset subsystems before shutting down.
     
    public void disabledInit(){
    }

    //This function is called periodically during operator control
     
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    //This function is called periodically during test mode
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
