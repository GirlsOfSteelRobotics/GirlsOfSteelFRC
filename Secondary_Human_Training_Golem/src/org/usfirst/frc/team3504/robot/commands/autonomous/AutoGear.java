package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear() {
    	addSequential(new DriveByMotionProfile("/home/lvuser/talonProfileLeft01.csv", "/home/lvuser/talonProfileRight01.csv"));
    	addSequential(new DriveByMotionProfile("/home/lvuser/talonProfileLeft02.csv", "/home/lvuser/talonProfileRight02.csv"));
    	addSequential(new DriveByMotionProfile("/home/lvuser/talonProfileLeft03.csv", "/home/lvuser/talonProfileRight03.csv"));
    	addSequential(new DriveByVision());
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
