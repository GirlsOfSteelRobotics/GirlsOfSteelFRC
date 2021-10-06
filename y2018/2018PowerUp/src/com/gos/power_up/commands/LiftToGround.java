package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToGround extends Command {
    private final Lift m_lift;


    public LiftToGround(Lift lift) {
        m_lift = lift;
        requires(m_lift);
    }


    @Override
    protected void initialize() {
        m_lift.setLiftToGround();
        System.out.println("LiftToGround");
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
        //Robot.lift.setSelectedSensorPosition(0,0,0);
    }


}
