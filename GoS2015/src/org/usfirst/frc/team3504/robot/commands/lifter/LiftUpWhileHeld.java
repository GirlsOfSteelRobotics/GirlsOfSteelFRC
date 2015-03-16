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
	}

	@Override
	protected void execute() {
		//Robot.lifter.moveUp();
	}

	@Override
	protected boolean isFinished() {
		return (Robot.lifter.isAtPosition());
		//return (Robot.lifter.isAtPosition());
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
