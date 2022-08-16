package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class TimeDelay extends CommandBase {

    private final double m_seconds;
    private final Timer m_tim;

    public TimeDelay(double seconds) {
        // Use requires() here to declare subsystem dependencies
        m_tim = new Timer();
        this.m_seconds = seconds;

    }

    // Called just before this Command runs the first times
    @Override
    public void initialize() {
        m_tim.start();
        System.out.println("TimeDelay Initialzed with " + m_seconds + " seconds as parameter");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_tim.get() > m_seconds);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_tim.stop();
        System.out.println("TimeDelay Finished");
    }


}
