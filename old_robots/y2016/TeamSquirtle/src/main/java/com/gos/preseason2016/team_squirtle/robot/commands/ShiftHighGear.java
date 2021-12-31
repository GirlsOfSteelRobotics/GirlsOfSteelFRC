package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Shifters;

/**
 *
 */
public class ShiftHighGear extends Command {

    private final Shifters m_shifters;

    public ShiftHighGear(Shifters shifters) {
        m_shifters = shifters;
        requires(m_shifters);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_shifters.shiftLeft(true);
        m_shifters.shiftRight(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
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
