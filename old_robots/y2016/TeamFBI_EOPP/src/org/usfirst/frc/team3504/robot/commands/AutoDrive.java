package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoDrive extends Command {

    public AutoDrive() {
        requires(Robot.drive);
    }
    @Override
    protected void initialize() {
        setTimeout(3);


    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub
        Robot.drive.driveAuto();
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub
        Robot.drive.stop();
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub
        end();
    }

}
