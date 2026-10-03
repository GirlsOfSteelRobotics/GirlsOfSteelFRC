package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ShooterRpmCommand extends Command {
    private final ShooterSubsystem m_shooter;
    private final double m_rpm;

    public ShooterRpmCommand(ShooterSubsystem shooter, double rpm) {
        m_shooter = shooter;
        m_rpm = rpm;

        addRequirements(shooter);
    }

    @Override
    public void execute() {
        // TODO implement
    }

    @Override
    public void end(boolean interrupted) {
        // TODO implement
    }
}
