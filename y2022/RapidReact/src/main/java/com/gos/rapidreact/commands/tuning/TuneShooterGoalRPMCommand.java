package com.gos.rapidreact.commands.tuning;

import com.gos.lib.properties.PropertyManager;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;


public class TuneShooterGoalRPMCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private static final PropertyManager.IProperty<Double> SHOOTER_GOAL = new PropertyManager.DoubleProperty("Tune Shooter RPM Goal", 0);

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
        double error = Math.abs(SHOOTER_GOAL.getValue() - m_shooter.getEncoderVelocity());
        return error < ShooterSubsystem.ALLOWABLE_ERROR;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
