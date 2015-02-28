package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftUpWhileHeld extends Command {

	double initialVal;
	public LiftUpWhileHeld() {
		requires(Robot.forklift);
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.forklift.up(1);
	}

	@Override
	protected boolean isFinished() {
		return Robot.forklift.isAtTop();
	}

	@Override
	protected void end() {
		Robot.forklift.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
	
}
