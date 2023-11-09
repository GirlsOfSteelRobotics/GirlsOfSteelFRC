package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class WristToSwitch extends Command {
    private final Wrist m_wrist;

    public WristToSwitch(Wrist wrist) {
        m_wrist = wrist;
        addRequirements(m_wrist);
    }


    @Override
    public void initialize() {
        m_wrist.setGoalWristPosition(Wrist.WRIST_IN_BOUND);
        System.out.println("WristToSwitch initialized");
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }



}
