package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class LiftToScale extends CommandBase {
    private final Lift m_lift;

    public LiftToScale(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }


    @Override
    public void initialize() {
        m_lift.setLiftToScale();
        System.out.println("Lift to Scale Init");
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
        System.out.println("Lift to Scale End");
    }


}
