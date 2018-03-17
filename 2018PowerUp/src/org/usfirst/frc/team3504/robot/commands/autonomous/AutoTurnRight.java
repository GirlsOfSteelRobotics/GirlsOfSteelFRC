package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionMagic;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnRight extends CommandGroup {

    public AutoTurnRight() {
    	System.out.println("AutoTurnRight starting");
    	
    	/*Position Control
    	addSequential(new DriveByMotionProfile("/home/lvuser/longTurn" + Robot.motionProfile + ".dat",
        		"/home/lvuser/shortTurn" + Robot.motionProfile + ".dat"));
        		*/
    	
    	//Motion Magic 
    	double heading = -90.0; //in degrees
    	double inches = 25.0;
    	addSequential(new DriveByMotionMagic(inches, heading));
    }
}
