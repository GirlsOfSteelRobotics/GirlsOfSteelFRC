package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class LiftUp extends CommandBase {
    private final Lift m_lift;


    public LiftUp(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }


    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        m_lift.holdLiftPosition();
        m_lift.incrementLift();
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
