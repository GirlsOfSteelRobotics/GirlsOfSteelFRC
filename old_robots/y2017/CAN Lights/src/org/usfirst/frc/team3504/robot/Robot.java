package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team3504.robot.subsystems.Lights;

public class Robot extends IterativeRobot {
    
    public static Lights lights;
    public static OI oi; 
    
    public void robotInit() {
       lights = new Lights();
       oi = new OI(); 
    } 

    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {

    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
     //   if (autonomousCommand != null) autonomousCommand.cancel();
        
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
    	
    }
}
 