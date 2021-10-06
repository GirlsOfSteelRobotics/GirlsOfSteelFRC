package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristToSwitch extends Command {
    private final Wrist m_wrist;

    public WristToSwitch(Wrist wrist) {
        m_wrist = wrist;
        requires(m_wrist);
    }


    @Override
    protected void initialize() {
        m_wrist.setGoalWristPosition(Wrist.WRIST_IN_BOUND);
        System.out.println("WristToSwitch initialized");
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }


    @Override
    protected void interrupted() {
    }
}
