package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class PusherOut extends Command {

    private final Manipulator m_manipulator;

    public PusherOut(Manipulator manipulator) {
        m_manipulator = manipulator;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        setTimeout(.5);
        m_manipulator.pusherOut();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_manipulator.pusherIn();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
