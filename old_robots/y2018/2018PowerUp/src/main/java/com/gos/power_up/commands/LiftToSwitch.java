package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class LiftToSwitch extends Command {
    private final Lift m_lift;


    public LiftToSwitch(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }


    @Override
    public void initialize() {
        m_lift.setLiftToSwitch();
        System.out.println("LiftToSwitch initialized");
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
