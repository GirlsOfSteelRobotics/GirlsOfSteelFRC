package com.gos.rapidreact.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.lib.properties.PropertyManager;


public class TuneShooterMotorSpeedCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;

    public static final PropertyManager.IProperty<Double> SHOOTER_SPEED = PropertyManager.createDoubleProperty(false, "Tune Shooter Motor Speed", 0);

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
