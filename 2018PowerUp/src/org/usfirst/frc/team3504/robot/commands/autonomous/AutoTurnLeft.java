package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnLeft extends CommandGroup {

    public AutoTurnLeft() {
    	System.out.println("AutoTurnLeft starting");
    	
    	/*Position Control
        addSequential(new DriveByMotionProfile("/home/lvuser/shortTurn" + Robot.motionProfile + ".dat",
        		"/home/lvuser/longTurn" + Robot.motionProfile + ".dat"));
        		*/
        
      //Motion Magic 
    	double heading = 90.0; //in degrees
    	double inches = 25.0;
    	addSequential(new DriveByMotionMagic(inches, heading));
    }
}
