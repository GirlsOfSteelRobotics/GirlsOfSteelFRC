package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class LiftToGround extends Command {
    private final Lift m_lift;


    public LiftToGround(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }


    @Override
    public void initialize() {
        m_lift.setLiftToGround();
        System.out.println("LiftToGround");
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
        //Robot.lift.setSelectedSensorPosition(0,0,0);
    }


}
