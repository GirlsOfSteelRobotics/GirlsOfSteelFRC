package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class ClimbDown extends CommandBase {
    private final Climber m_climber;

    public ClimbDown(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }


    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        m_climber.climb(-1.0);
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_climber.stopClimb();
    }



}
