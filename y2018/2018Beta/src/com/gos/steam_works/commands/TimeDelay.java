package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TimeDelay extends Command {

    private final double m_seconds;
    private final Timer m_tim;

    public TimeDelay(double seconds) {
        // Use requires() here to declare subsystem dependencies
        m_tim = new Timer();
        this.m_seconds = seconds;

    }

    // Called just before this Command runs the first times
    @Override
    protected void initialize() {
        m_tim.start();
        System.out.println("TimeDelay Initialzed with " + m_seconds + " seconds as parameter");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_tim.get() > m_seconds;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_tim.stop();
        System.out.println("TimeDelay Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
