package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/*
 *
 */
public class AutoLifterDownToBottom extends Command {

    private final Lifter m_lifter;

    public AutoLifterDownToBottom(Lifter lifter) {
        m_lifter = lifter;
        requires(m_lifter);
    }

    @Override
    protected void initialize() {
        m_lifter.setPosition(Lifter.DISTANCE_ZERO_TOTES);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return (m_lifter.isAtPosition() || m_lifter.isAtBottom());
    }

    @Override
    protected void end() {
        m_lifter.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
