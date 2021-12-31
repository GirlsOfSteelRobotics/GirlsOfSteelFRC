package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Climber;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbDown extends Command {
    private final Climber m_climber;

    public ClimbDown(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }


    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        m_climber.climb(-1.0);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_climber.stopClimb();
    }



}
