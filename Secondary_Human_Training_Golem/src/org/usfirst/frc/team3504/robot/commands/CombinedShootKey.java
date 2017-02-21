package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CombinedShootKey extends CommandGroup {

    public CombinedShootKey() {
    	addParallel(new Shoot());
    	addSequential(new TimeDelay(0.5)); //TODO add a time delay of .25 seconds
    	addSequential(new LoadBall()); 
    	
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
