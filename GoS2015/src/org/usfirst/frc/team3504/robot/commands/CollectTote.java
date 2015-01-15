package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class CollectTote extends Command {

	public CollectTote() {
		requires(Robot.sucker);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.sucker.suckToteIn();
	}

	@Override
	protected boolean isFinished() {
		Robot.sucker.getLimit();
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
