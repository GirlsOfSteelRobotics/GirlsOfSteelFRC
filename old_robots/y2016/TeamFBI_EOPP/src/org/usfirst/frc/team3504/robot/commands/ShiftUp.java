package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftUp extends Command {

    public ShiftUp() {
        requires(Robot.shifters);
    }
    @Override
    protected void initialize() {
        Robot.shifters.shiftLeft(true);
        Robot.shifters.shiftRight(true);
        // TODO Auto-generated method stub

    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void interrupted() {
        end();
        // TODO Auto-generated method stub

    }

}
