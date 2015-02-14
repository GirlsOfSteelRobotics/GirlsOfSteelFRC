package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Authors: Alexa, Corinne, Sarah
 */
public class AutoToteAndContainer extends CommandGroup {
    
	//collects one tote and one container and takes them to the auto zone 
	
    public  AutoToteAndContainer() {
    	addSequential(new AutoCollector());
    	addSequential(new Lifting()); 
    	addSequential(new AutoFirstPickup());
    	addSequential(new AutoCollector());
    	addSequential(new Lifting());
    	addSequential(new AutoDriveLeft()); 
    }
}
