package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear(String leftFile, String rightFile) {
    	
    	addSequential(new DriveByMotionProfile(leftFile, rightFile));
    	addSequential(new DriveByVision()); //this is a while held button, so this might not work since it needs to run more than once... TODO: fix?
    	//addSequential(new DriveByDistance(-4.0)); //TODO: adjust this value
    	
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
