package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Consider using limit switches
 */
public class ReleaseTote extends Command {

	@Override
	protected void initialize() {
		requires(Robot.sucker);
	}

	@Override
	protected void execute() {
		Robot.sucker.suckToteOut();
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
