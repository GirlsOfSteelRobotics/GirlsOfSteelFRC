package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class StopShooter extends Command {

    private final Manipulator m_manipulator;

    public StopShooter(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_manipulator.stopCollector();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
