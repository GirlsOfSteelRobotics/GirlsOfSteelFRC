package com.gos.rapidreact.commands.tuning;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class TuneShooterMotorSpeedCommand extends Command {
    private final ShooterSubsystem m_shooter;

    public static final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(false, "Tune Shooter Motor Speed", 0);

    public TuneShooterMotorSpeedCommand(ShooterSubsystem shooterSubsystem) {
        this.m_shooter = shooterSubsystem;
        addRequirements(this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterSpeed(SHOOTER_SPEED.getValue());
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
