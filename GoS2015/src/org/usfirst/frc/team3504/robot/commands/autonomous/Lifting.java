package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorIn;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.doors.DoorsIn;
import org.usfirst.frc.team3504.robot.commands.doors.DoorsOut;
import org.usfirst.frc.team3504.robot.commands.fingers.FingerDown;
import org.usfirst.frc.team3504.robot.commands.fingers.FingerUp;
import org.usfirst.frc.team3504.robot.commands.shack.ShackIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Lifting extends CommandGroup {
    
    public  Lifting() {
    	addSequential(new DoorsIn());
    	
    	addSequential(new FingerUp());
    	
    	addSequential(new AutoReleaseGripper());
    	
    	addSequential(new AngleCollectorIn());
    	
    	addSequential(new AutoCollector());
    	
    	addSequential(new ShackIn());

    	//addSequential(new AutoBringInGrippers());
    	
    	addSequential(new AngleCollectorOut());
    	
    	addSequential(new AutoLift());
    	
    	//addSequential(new AutoReleaseGripper());
    	
    	addSequential(new AutoLowerLifter());
    	
    	addSequential(new DoorsIn());
    }
}
