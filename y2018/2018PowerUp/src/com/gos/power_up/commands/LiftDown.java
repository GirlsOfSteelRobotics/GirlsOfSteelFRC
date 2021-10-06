package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftDown extends Command {
    private final Lift m_lift;


    public LiftDown(Lift lift) {
        m_lift = lift;
        requires(m_lift);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_lift.holdLiftPosition();
        m_lift.decrementLift();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
    }


}
