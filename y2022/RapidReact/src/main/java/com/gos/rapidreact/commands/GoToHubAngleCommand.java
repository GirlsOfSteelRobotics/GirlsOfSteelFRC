package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class GoToHubAngleCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final double m_angle;
    private boolean m_atPosition;

    public GoToHubAngleCommand(ChassisSubsystem chassisSubsystem, double angle) {
        this.m_chassis = chassisSubsystem;
        m_angle = angle;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_atPosition = m_chassis.turnPID(m_angle);
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
