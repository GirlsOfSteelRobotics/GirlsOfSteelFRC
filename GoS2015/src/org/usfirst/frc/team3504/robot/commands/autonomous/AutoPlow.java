package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.doors.DoorsOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Authors: Alexa, Corinne, Kyra, Sarah
 */
public class AutoPlow extends CommandGroup {
    
	//collects one container and three totes and takes them to the autozone
	

    public AutoPlow() {

    	addSequential(new AutoCollector());
    	addSequential(new Lifting()); 
    	addSequential(new AutoFirstPickup());
    	addSequential(new AutoCollector());
    	addSequential(new Lifting());
     	//used to get first can and tote

    	addSequential(new AutoCollector());
    	addSequential(new AutoDriveForward(55.25));
    	addSequential(new Lifting()); 
    	//gets middle tote assuming partner cleared second can
    	
    	addSequential(new AutoCollector());
    	addSequential(new AutoDriveForward(55.25));
    	addSequential(new Lifting());
    	//gets last tote assuming partner cleared third can  
    	
    	addSequential(new AutoDriveLeft());
    	addParallel(new DoorsOut());
    	addSequential(new Release()); 
    	addSequential(new AutoDriveBackwards());
    	//turn into the autozone to get robot set
    }
}
