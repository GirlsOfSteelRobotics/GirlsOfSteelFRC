package com.gos.rapidreact.commands.tuning;

import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;


public class TuneShooterGoalRPMCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private static final PropertyManager.IProperty<Double> SHOOTER_GOAL = PropertyManager.createDoubleProperty(false, "Tune Shooter RPM Goal", 0);

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
