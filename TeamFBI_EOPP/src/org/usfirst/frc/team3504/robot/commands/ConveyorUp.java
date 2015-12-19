package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ConveyorUp extends Command {

	public ConveyorUp() {
		requires(Robot.manipulator);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		Robot.manipulator.manipulatorConveyorBeltMotorLeft(true);
		Robot.manipulator.manipulatorConveyorBeltMotorRight(true);

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
		Robot.manipulator.stopConveyorBeltMotor();
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
