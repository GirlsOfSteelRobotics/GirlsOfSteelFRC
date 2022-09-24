package com.gos.codelabs.pid.commands.tuning;

import com.gos.codelabs.pid.subsystems.ShooterSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FindShooterFFGainCommand extends CommandBase {

    private static final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(false, "Tuning.Shooter.FFSpeed", 0);
    private final ShooterSubsystem m_shooter;

    public FindShooterFFGainCommand(ShooterSubsystem shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void execute() {
        m_shooter.setPercentOutput(SHOOTER_SPEED.getValue());
    }

}
