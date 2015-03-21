package org.usfirst.frc.team3504.robot.commands.doors;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DoorsIn extends Command {

	public DoorsIn() {
		requires(Robot.doors);
	}

	@Override
	protected void initialize() {
		Robot.doors.doorsIn();
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
	}

}
