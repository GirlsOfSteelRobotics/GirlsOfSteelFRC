package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.commands.Drive;
import org.usfirst.frc.team3504.robot.commands.PivotDown;
import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.ReleaseBall;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSpyBot extends CommandGroup {
	
    public  AutoSpyBot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new Drive(10,0));  //Drive until across from goal. Goal should be on right side of robot.
    	addSequential(new Drive(10, -270));  //Drive until angle faces goal.
    	addSequential(new Drive(10, 10));  //drive completely to goal.
    	addSequential(new PivotDown()); //The pivot goes down.
    	addSequential(new ReleaseBall()); //The claws open and the ball is released.
    	addSequential(new PivotUp()); //The pivot goes back up.
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
