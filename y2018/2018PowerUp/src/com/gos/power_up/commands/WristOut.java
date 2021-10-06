package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristOut extends Command {
    private final Wrist m_wrist;

    public WristOut(Wrist wrist) {
        m_wrist = wrist;
        requires(m_wrist);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_wrist.holdWristPosition();
        m_wrist.wristOut();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {

    }


}
