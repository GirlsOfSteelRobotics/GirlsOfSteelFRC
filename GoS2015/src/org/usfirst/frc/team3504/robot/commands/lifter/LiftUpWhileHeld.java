package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftUpWhileHeld extends Command {

	double initialVal;
	public LiftUpWhileHeld() {
		requires(Robot.lifter);
	}

	@Override
	protected void initialize() {
		Robot.lifter.setPosition(Lifter.DISTANCE_FOUR_TOTES);
	//	Robot.lifter.moveUp();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return (Robot.lifter.isAtPosition());
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
