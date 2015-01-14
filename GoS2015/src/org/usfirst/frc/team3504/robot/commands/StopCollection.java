package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopCollection extends Command {

	@Override
	protected void initialize() {
		requires(Robot.sucker);
	}

	@Override
	protected void execute() {
		Robot.sucker.stopSucking();
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
