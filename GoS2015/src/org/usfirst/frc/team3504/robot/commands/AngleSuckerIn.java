package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Command order for suckers should be changed based on what
 * the drivers want -> could use a command group
 */
public class AngleSuckerIn extends Command {
	
	public AngleSuckerIn() {
		requires(Robot.sucker);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.sucker.suckerAngleIn();
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
