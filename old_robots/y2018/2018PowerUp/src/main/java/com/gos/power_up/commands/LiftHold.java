package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class LiftHold extends Command {
    private final Lift m_lift;

    public LiftHold(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }


    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        m_lift.holdLiftPosition();

    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
