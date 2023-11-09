package com.scra.mepi.rapid_react.commands;

import com.scra.mepi.rapid_react.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class ClimbPIDComand extends Command {
    private final ClimberSubsystem m_climberSubsystem;
    private final double m_goal;

    public ClimbPIDComand(ClimberSubsystem climberSubsystem, double goal) {
        m_climberSubsystem = climberSubsystem;
        m_goal = goal;
        addRequirements(climberSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_climberSubsystem.runClimberPID(m_goal);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_climberSubsystem.getPIDPosition()) < 100 || (m_climberSubsystem.leftLimitSwitchPress() || m_climberSubsystem.rightLimitSwitchPress());
    }

    @Override
    public void end(boolean interrupted) {
        m_climberSubsystem.set(0);
    }


}
