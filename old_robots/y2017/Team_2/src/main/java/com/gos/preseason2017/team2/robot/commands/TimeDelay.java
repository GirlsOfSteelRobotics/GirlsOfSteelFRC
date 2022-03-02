package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class TimeDelay extends CommandBase {

    private final double m_seconds;

    public TimeDelay(double seconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.m_seconds = seconds;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        setTimeout(m_seconds);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
