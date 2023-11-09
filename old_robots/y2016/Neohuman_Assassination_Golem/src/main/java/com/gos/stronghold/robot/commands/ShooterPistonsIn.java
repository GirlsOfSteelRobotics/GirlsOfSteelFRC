package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Shooter;

/**
 *
 */
public class ShooterPistonsIn extends Command {

    private final Shooter m_shooter;

    public ShooterPistonsIn(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_shooter.pistonsIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
