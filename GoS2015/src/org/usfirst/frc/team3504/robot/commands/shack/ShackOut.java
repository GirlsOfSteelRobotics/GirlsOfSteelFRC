package org.usfirst.frc.team3504.robot.commands.shack;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Arushi
 */
public class ShackOut extends Command {

	public ShackOut() {
		requires(Robot.shack);
	}

	protected void initialize() {
		Robot.shack.ShackOut();
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
