package com.gos.rapidreact.commands.tuning;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.lib.properties.PropertyManager;


public class TuneShooterMotorSpeedCommand extends CommandBase {
    private final ShooterSubsystem m_shooterSubsystem;

    public static final PropertyManager.IProperty<Double> SHOOTER_SPEED = new PropertyManager.DoubleProperty("Motor Speed", 0);

    public TuneShooterMotorSpeedCommand(ShooterSubsystem shooterSubsystem) {
        this.m_shooterSubsystem = shooterSubsystem;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_shooterSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooterSubsystem.setShooterSpeed(SHOOTER_SPEED.getValue());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterSubsystem.setShooterSpeed(0);
    }
}
