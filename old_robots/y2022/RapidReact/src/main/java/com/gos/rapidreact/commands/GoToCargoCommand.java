package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class GoToCargoCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final double m_xCoordinate;
    private final double m_yCoordinate;
    private boolean m_atPosition;

    public GoToCargoCommand(ChassisSubsystem chassisSubsystem, double xCoordinate, double yCoordinate) {
        this.m_chassis = chassisSubsystem;
        m_xCoordinate = xCoordinate;
        m_yCoordinate = yCoordinate;
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_atPosition = m_chassis.goToCargo(m_xCoordinate, m_yCoordinate);
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
