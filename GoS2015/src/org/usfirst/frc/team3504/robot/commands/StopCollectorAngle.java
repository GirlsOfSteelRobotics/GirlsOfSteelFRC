package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopCollectorAngle extends Command {

	@Override
	protected void initialize() {
		requires(Robot.collector);
	}

	@Override
	protected void execute() {
		Robot.collector.stopAngle();
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
