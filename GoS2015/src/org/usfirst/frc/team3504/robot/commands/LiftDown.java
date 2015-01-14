package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/*
 * Consider using limit switches; need to add a stop talons method in the subsystem
 */
public class LiftDown extends Command{

	public LiftDown() {
		requires(Robot.forklift);
	}
	
	@Override
	protected void initialize() {
		setTimeout(2);
	}

	@Override
	protected void execute() {
		Robot.forklift.down(-0.5);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		//Stop talons
	}

	@Override
	protected void interrupted() {
		end();
	}

}
