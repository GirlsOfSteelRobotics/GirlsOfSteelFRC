package com.gos.recycle_rush.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/*
 *
 */
public class AutoLifterUpToTop extends CommandBase {

    private final Lifter m_lifter;

    public AutoLifterUpToTop(Lifter lifter) {
        m_lifter = lifter;
        addRequirements(m_lifter);
    }

    @Override
    public void initialize() {
        m_lifter.setPosition(Lifter.DISTANCE_FOUR_TOTES);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return (m_lifter.isAtPosition() || m_lifter.isAtTop());
    }

    @Override
    public void end(boolean interrupted) {
        m_lifter.stop();
    }



}
