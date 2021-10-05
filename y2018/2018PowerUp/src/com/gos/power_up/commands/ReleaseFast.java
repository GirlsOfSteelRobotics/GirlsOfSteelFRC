package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseFast extends Command {

    private final double speed;

    public ReleaseFast() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        speed = 0.9;
        requires(Robot.collector);
    }

    public ReleaseFast(double s) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        speed = s;
        requires(Robot.collector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Release");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.collector.release(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.collector.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
