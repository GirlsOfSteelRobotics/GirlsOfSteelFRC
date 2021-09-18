package org.usfirst.frc.team3504.robot.commands.buttons;

import org.usfirst.frc.team3504.robot.commands.DriveBackward;
import org.usfirst.frc.team3504.robot.commands.camera.SwitchToCamFlap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchToBackward extends CommandGroup {
    
    public  SwitchToBackward() {
    	addParallel(new SwitchToCamFlap());
    	addParallel(new DriveBackward());
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
