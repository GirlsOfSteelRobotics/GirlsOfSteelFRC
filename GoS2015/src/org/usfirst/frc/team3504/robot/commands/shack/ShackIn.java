package org.usfirst.frc.team3504.robot.commands.shack;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShackIn extends Command {

    public ShackIn() {
    	requires(Robot.shack); 
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.shack.in(); 
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.shack.stop();
    }

    protected void interrupted() {
    	end();
    }
}
