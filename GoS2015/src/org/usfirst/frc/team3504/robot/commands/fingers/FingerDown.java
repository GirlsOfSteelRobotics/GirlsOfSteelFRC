package org.usfirst.frc.team3504.robot.commands.fingers;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@annika
 *@ziya
 */
public class FingerDown extends Command {

    public FingerDown() {
    	requires(Robot.fingers);
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.fingers.fingerDown();
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
