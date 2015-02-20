package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.doors.DoorsOut;
import org.usfirst.frc.team3504.robot.commands.fingers.FingerDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Release extends CommandGroup {
    
    public  Release() {
    	
    	addSequential(new AutoReleaseGripper());
    	
    	addSequential(new AngleCollectorOut());
    	
    	addSequential(new FingerDown());
    	
    	addSequential(new DoorsOut());
    }
}
