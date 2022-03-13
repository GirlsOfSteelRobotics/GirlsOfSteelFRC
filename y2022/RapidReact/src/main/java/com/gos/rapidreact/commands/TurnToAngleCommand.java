package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class TurnToAngleCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final double m_angleDeg;
    private boolean m_atPosition;

    public TurnToAngleCommand(ChassisSubsystem chassisSubsystem, double angle) {
        this.m_chassis = chassisSubsystem;
        m_angleDeg = angle;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_atPosition = m_chassis.turnPID(m_angleDeg);
    }

    @Override
    public boolean isFinished() {
        return m_atPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setArcadeDrive(0, 0);
    }
}
