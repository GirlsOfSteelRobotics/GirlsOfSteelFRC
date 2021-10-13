package org.usfirst.frc.team3504.robot.commands.collector;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Command order for suckers should be changed based on what
 * the drivers want -> could use a command group
 * 
 */
public class AngleCollectorIn extends Command {

	public AngleCollectorIn() {
		requires(Robot.collector);
	}

	@Override
	protected void initialize() {
		// Only needs to run once to move collector pneumatic in
		Robot.collector.collectorIn();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}
