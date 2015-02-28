package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftDownWhileHeld extends Command{

	public LiftDownWhileHeld() {
		requires(Robot.forklift);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.forklift.down(1);
	}

	@Override
	protected boolean isFinished() {
		return Robot.forklift.isAtBottom();
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
