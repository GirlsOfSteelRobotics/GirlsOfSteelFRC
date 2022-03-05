package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;


public class ShootFromTableCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private final ShooterLimelightSubsystem m_limelight;

    public ShootFromTableCommand(ShooterSubsystem shooterSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem) {
        m_shooter = shooterSubsystem;
        m_limelight = shooterLimelightSubsystem;

        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.rpmForDistance(m_limelight.getDistanceToHub());

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.setShooterSpeed(0);

    }
}
