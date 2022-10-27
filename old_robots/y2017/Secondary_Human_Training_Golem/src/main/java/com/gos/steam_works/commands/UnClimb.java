package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.subsystems.Climber;

/**
 *
 */
public class UnClimb extends CommandBase {

    private final Climber m_climber;

    public UnClimb(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_climber.climb(0.75);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_climber.stopClimb();
    }


}
