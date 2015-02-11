package org.usfirst.frc.team3504.robot.commands.collector;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class CollectTote extends Command {

	public CollectTote() {
		requires(Robot.collector);
	}
	
	@Override
	protected void initialize() {
		setTimeout(2.5);
	}

	@Override
	protected void execute() {
		Robot.collector.collectorToteIn();
	}

	@Override
	protected boolean isFinished() {
//		return Robot.collector.getLimit();
		return isTimedOut();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}
