package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveBackward extends Command {

	public DriveBackward()
	{
		requires(Robot.chassis);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		Robot.chassis.driveBackward();
		Timer.delay(2);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.chassis.stop();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
