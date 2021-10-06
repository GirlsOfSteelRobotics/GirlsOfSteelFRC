package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToScale extends Command {
    private final Lift m_lift;

    public LiftToScale(Lift lift) {
        m_lift = lift;
        requires(m_lift);
    }


    @Override
    protected void initialize() {
        m_lift.setLiftToScale();
        System.out.println("Lift to Scale Init");
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
        System.out.println("Lift to Scale End");
    }


}
