package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReleaseTote extends Command {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
	}

	@Override
	protected void interrupted() {
		end();

	}

}
