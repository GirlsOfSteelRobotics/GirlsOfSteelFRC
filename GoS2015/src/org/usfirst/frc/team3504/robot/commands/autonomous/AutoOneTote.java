package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.autonomous.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Authors: Alexa, Corinne, Sarah
 */
public class AutoOneTote extends CommandGroup {
    
    //Collects one tote/container and takes it to the auto zone
	
	public  AutoOneTote() {
    	addSequential(new AutoCollector());
    	addSequential(new Lifting());
    	addSequential(new AutoTurnRight());
    	addSequential(new Release()); 
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
