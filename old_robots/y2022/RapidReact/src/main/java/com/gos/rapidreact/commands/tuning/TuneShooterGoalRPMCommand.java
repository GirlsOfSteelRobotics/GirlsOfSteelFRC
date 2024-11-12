package com.gos.rapidreact.commands.tuning;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class TuneShooterGoalRPMCommand extends Command {
    private final ShooterSubsystem m_shooter;
    private static final GosDoubleProperty SHOOTER_GOAL = new GosDoubleProperty(false, "Tune Shooter RPM Goal", 0);

    public TuneShooterGoalRPMCommand(ShooterSubsystem shooterSubsystem) {
        this.m_shooter = shooterSubsystem;

        addRequirements(this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterRpmPIDSpeed(SHOOTER_GOAL.getValue());
    }

    @Override
    public boolean isFinished() {
        // Run it forever, for tuning
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.setShooterSpeed(0);
    }
}
