package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;

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
		Robot.lifter.setPosition(Lifter.DISTANCE_ZERO_TOTES);
	}

	@Override
	protected void execute() {
		//Robot.lifter.moveDown();
	}

	@Override
	protected boolean isFinished() {
		//return (Robot.lifter.isAtBottom());
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
