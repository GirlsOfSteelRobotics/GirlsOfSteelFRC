package org.usfirst.frc.team3504.robot.commands.drive;

import org.usfirst.frc.team3504.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveBackward extends Command {

    public DriveBackward() {
        requires(Robot.chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.chassis.driveBackward();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.chassis.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
