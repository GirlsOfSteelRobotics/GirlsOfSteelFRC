package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/*
 * 
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
		Robot.forklift.getLimit(); //might not be useds, depends 
		return isTimedOut();
	}

	@Override
	protected void end() {
		//Stop talons
		Robot.forklift.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
