package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class AngleCollectorOut extends Command{

	public AngleCollectorOut() {
		requires(Robot.collector);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.collector.collectorAngleOut();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();	
	}

}
