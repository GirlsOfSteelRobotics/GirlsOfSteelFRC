package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftUp extends Command {

	public LiftUp() {
		requires(Robot.forklift);
	}

	@Override
	protected void initialize() {
		setTimeout(2);
	}

	@Override
	protected void execute() {
		Robot.forklift.up(0.5);
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		//Stop motors
		Robot.forklift.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
