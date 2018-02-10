package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot.PlateSide;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSwitchOne extends CommandGroup {

    public AutoSwitchOne(PlateSide plateSide) {
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
    		if(plateSide == PlateSide.left)
		{
			addSequential(new DriveByDistance(30, Shifters.Speed.kLow)); //TODO: change in.
			addSequential(new DriveByMotionProfile("","")); //TODO: change arguments (90 degree turn CW)
		}
		else
		{
			addSequential(new DriveByDistance(20, Shifters.Speed.kLow)); //TODO: change in.
			addSequential(new DriveByMotionProfile("","")); //TODO: change arguments (90 degree turn CW)
			addSequential(new DriveByDistance(56, Shifters.Speed.kLow)); //TODO: change in.
			addSequential(new DriveByMotionProfile("","")); //TODO: change arguments (90 degree turn CCW)
			addSequential(new DriveByDistance(20, Shifters.Speed.kLow)); //TODO: change in.
			//turn in place??
		}
    }
}
