package org.usfirst.frc.team3504.robot.commands.fingers;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author arushibandi
 */
public class FingerUp extends Command {

    public FingerUp() {
    	requires(Robot.fingers);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.fingers.fingerUp();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
