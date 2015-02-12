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
    }

    protected void execute() {
    	Robot.shack.out();
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
