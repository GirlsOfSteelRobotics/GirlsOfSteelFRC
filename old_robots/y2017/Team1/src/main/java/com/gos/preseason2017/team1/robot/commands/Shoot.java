package com.gos.preseason2017.team1.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2017.team1.robot.subsystems.Shooter;

/**
 *
 */
public class Shoot extends CommandBase {

    private final Shooter m_shooter;

    public Shoot(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_shooter.pistonOut();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

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
