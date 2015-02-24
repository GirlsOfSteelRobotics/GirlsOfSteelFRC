package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.doors.DoorsOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Authors: Alexa, Corinne, Kyra, Sarah
 */
public class AutoPlow extends CommandGroup {
    
	//collects one container and three totes and takes them to the autozone
	double distanceFwd1;
	double distanceFwd2;
	double distanceLeft1;
	double distanceBack1; 
	double distanceFirst1; 

    public AutoPlow() {
    	distanceFwd1 = 55.25;
    	distanceFwd2 = 55.25; 
    	distanceLeft1 = 107; 
    	distanceBack1 = 50; 
    	
    	addSequential(new AutoCollector());
    	addSequential(new Lifting()); 
    	addSequential(new AutoFirstPickup(distanceFirst1));
    	addSequential(new AutoCollector());
    	addSequential(new Lifting());
     	//used to get first can and tote

    	addSequential(new AutoCollector());
    	addSequential(new AutoDriveForward(distanceFwd1)); 
    	addSequential(new Lifting()); 
    	//gets middle tote assuming partner cleared second can
    	
    	addSequential(new AutoCollector());
    	addSequential(new AutoDriveForward(distanceFwd2));
    	addSequential(new Lifting());
    	//gets last tote assuming partner cleared third can  
    	
    	addSequential(new AutoDriveLeft(distanceLeft1));
    	addParallel(new DoorsOut());
    	addSequential(new Release()); 
    	addSequential(new AutoDriveBackwards(distanceBack1));
    	//turn into the autozone to get robot set
    }
}
