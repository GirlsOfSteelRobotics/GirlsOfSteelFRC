package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class UnClimb extends CommandBase {

    private final Climber m_climber;

    public UnClimb(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }


    @Override
    public void initialize() {

    }


    @Override
    public void execute() {
        m_climber.climb(0.75);
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
