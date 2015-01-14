package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * Make sure that this is a while held button
 */
public class DriveLeft extends Command {
	
	public DriveLeft() {
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.chassis.driveLeft();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.chassis.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
