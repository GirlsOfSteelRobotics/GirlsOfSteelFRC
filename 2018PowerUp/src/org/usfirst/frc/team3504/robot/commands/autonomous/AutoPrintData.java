package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPrintData extends CommandGroup {

    public AutoPrintData() {
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
    	
    	if (Robot.getSwitchSide() == Robot.PlateSide.left) System.out.println("Switch is on the left");
    	else if (Robot.getSwitchSide() == Robot.PlateSide.right) System.out.println("Switch is on the right");
    	else if (Robot.getSwitchSide() == Robot.PlateSide.bad) System.out.println("Switch is BAD");
    	
    	if (Robot.getScaleSide() == Robot.PlateSide.left) System.out.println("Scale is on the left");
    	else if (Robot.getScaleSide() == Robot.PlateSide.right) System.out.println("Scale is on the right");
    	else if (Robot.getScaleSide() == Robot.PlateSide.bad) System.out.println("Scale is BAD");
    }
}
