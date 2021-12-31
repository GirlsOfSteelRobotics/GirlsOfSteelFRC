package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.steam_works.robot.subsystems.Shooter;

/**
 *
 */
public class DecrementHighShooter extends Command {

    private final Shooter m_shooter;

    public DecrementHighShooter(Shooter shooter) {
        m_shooter = shooter;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_shooter.decrementHighShooterSpeed();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
