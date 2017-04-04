package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CombinedShoot extends CommandGroup {

	public CombinedShoot() {
		addParallel(new Shoot(Shooter.SHOOTER_DEFAULT_SPEED));
		addSequential(new TimeDelay(0.75));
		addParallel(new Agitate());
		addSequential(new LoadBall());

		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
