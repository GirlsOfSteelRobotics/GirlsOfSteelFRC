package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.LiftHold;
import org.usfirst.frc.team3504.robot.commands.LiftToSwitch;
import org.usfirst.frc.team3504.robot.commands.Release;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.WristHold;
import org.usfirst.frc.team3504.robot.commands.WristToCollect;
import org.usfirst.frc.team3504.robot.commands.WristToSwitch;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSwitchSimple extends CommandGroup {

    public AutoSwitchSimple() {
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
    	
    	

    	
    	addSequential(new WristToCollect());
    	addSequential(new LiftToSwitch());
    	addParallel(new WristHold());
    	addParallel(new LiftHold());
    	addSequential(new DriveByDistance(105, Shifters.Speed.kLow));
    	addParallel(new Release());
    	addSequential(new TimeDelay(1.0));
    	addSequential(new DriveByDistance(-30, Shifters.Speed.kLow));
    }
}
