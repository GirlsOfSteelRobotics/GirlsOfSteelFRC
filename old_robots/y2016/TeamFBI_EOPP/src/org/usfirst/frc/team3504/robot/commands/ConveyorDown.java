package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ConveyorDown extends Command {

	public ConveyorDown() {
		requires(Robot.manipulator);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.manipulator.manipulatorConveyorBeltMotorLeft(false);
		Robot.manipulator.manipulatorConveyorBeltMotorRight(false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.manipulator.stopConveyorBeltMotor();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
