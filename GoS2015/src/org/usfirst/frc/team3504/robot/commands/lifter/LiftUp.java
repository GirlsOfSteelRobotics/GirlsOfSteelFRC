package org.usfirst.frc.team3504.robot.commands.lifter;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/*
 * 
 */
public class LiftUp extends Command {

	double initialVal;
	
	public LiftUp() {
		requires(Robot.forklift);
		//initialVal = Robot.forklift.getLiftEncoder();
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
		//return (Robot.forklift.getLiftEncoder-initialVal)==50; TODO: set to distance lifter needs to go up to be able to drop tote back down to fingers
		
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
