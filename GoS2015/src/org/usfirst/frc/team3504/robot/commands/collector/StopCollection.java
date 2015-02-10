package org.usfirst.frc.team3504.robot.commands.collector;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopCollection extends Command {
	
	public StopCollection() {
		requires(Robot.collector);		
	}

	@Override
	protected void initialize() {
		Robot.collector.stopCollecting();
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
