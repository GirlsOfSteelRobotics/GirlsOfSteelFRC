package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
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
    	addSequential(new DriveByDistance(10.0, Shifters.Speed.kLow));
    	//addSequential(new TimeDelay(10));
    	addSequential(new DriveByMotionProfile("/home/lvuser/longTurn" + Robot.motionProfile + ".dat",
        		"/home/lvuser/shortTurn" + Robot.motionProfile + ".dat"));
    }
}
