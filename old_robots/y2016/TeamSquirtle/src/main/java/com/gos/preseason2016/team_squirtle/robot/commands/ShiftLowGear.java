package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Shifters;

/**
 *
 */
public class ShiftLowGear extends Command {

    private final Shifters m_shifters;

    public ShiftLowGear(Shifters shifters) {
        m_shifters = shifters;
        requires(m_shifters);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_shifters.shiftLeft(false);
        m_shifters.shiftRight(false);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
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
    }
}
