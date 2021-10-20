package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ShootBall extends CommandGroup {

    public  ShootBall() {
        //operator lines up pivot
        addSequential(new ShooterPistonsOut()); //tilt shooter partially so that ball doesn't fall out
        addSequential(new ReleaseBall()); //need to test exact seconds for delay
        addParallel(new ShooterPistonsIn()); //tilts shooter all the way up
        addParallel(new WaitCommand(5));
        addSequential(new StopShooterWheels());
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
