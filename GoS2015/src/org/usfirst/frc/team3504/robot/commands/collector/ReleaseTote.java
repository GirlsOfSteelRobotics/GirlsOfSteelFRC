package org.usfirst.frc.team3504.robot.commands.collector;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Consider using limit switches
 */
public class ReleaseTote extends Command {

	@Override
	protected void initialize() {
		requires(Robot.collector);
	}

	@Override
	protected void execute() {
		Robot.collector.collectorToteOut();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.collector.stopCollecting();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
