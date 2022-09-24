package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ShooterSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TuneShooterRpmCommand extends CommandBase {
    private static final GosDoubleProperty SHOOTER_RPM = new GosDoubleProperty(false, "Tuning.Shooter.RPM", 1500);

    private final ShooterSubsystem m_spinningWheelSubsystem;

    public TuneShooterRpmCommand(ShooterSubsystem spinningWheelSubsystem) {
        m_spinningWheelSubsystem = spinningWheelSubsystem;
        addRequirements(this.m_spinningWheelSubsystem);
    }

    @Override
    public void execute() {
        m_spinningWheelSubsystem.spinAtRpm(SHOOTER_RPM.getValue());
    }
}
