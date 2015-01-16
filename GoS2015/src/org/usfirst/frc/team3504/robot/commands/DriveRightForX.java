package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveRightForX extends Command{

	public DriveRightForX() {
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		setTimeout(2);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.chassis.driveRight();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
	
}
