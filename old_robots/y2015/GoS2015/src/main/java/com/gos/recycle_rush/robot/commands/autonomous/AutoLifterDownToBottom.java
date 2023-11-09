package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/*
 *
 */
public class AutoLifterDownToBottom extends Command {

    private final Lifter m_lifter;

    public AutoLifterDownToBottom(Lifter lifter) {
        m_lifter = lifter;
        addRequirements(m_lifter);
    }

    @Override
    public void initialize() {
        m_lifter.setPosition(Lifter.DISTANCE_ZERO_TOTES);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return (m_lifter.isAtPosition() || m_lifter.isAtBottom());
    }

    @Override
    public void end(boolean interrupted) {
        m_lifter.stop();
    }



}
