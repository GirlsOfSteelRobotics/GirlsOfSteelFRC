package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftUp extends Command {
    private final Lift m_lift;


    public LiftUp(Lift lift) {
        m_lift = lift;
        requires(m_lift);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_lift.holdLiftPosition();
        m_lift.incrementLift();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
    }


}
