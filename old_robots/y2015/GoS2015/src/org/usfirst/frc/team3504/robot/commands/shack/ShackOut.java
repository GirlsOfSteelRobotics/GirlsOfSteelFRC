package org.usfirst.frc.team3504.robot.commands.shack;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShackOut extends Command {

    public ShackOut() {
        requires(Robot.shack);
    }

    @Override
    protected void initialize() {
        Robot.shack.ShackOut();
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
