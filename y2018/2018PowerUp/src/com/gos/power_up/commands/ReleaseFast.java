package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseFast extends Command {

    private final double m_speed;

    public ReleaseFast() {
        this(0.9);
    }

    public ReleaseFast(double s) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_speed = s;
        requires(Robot.m_collector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Release");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.m_collector.release(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_collector.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
