package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ConveyorDown extends Command {

	public ConveyorDown() {
		requires(Robot.manipulator);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.manipulator.manipulatorConveyorBeltMotorLeft(false);
		Robot.manipulator.manipulatorConveyorBeltMotorRight(false);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();

	}

}
