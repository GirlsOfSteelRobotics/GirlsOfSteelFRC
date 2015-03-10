package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftDownWhileHeld extends Command{

	public LiftDownWhileHeld() {
		requires(Robot.lifter);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.lifter.down(1);
	}

	@Override
	protected boolean isFinished() {
		return Robot.lifter.isAtBottom();
	}

	@Override
	protected void end() {
		Robot.lifter.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
