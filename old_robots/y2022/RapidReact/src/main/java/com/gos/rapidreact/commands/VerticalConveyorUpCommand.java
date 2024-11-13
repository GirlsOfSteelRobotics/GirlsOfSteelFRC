package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class VerticalConveyorUpCommand extends Command {
    private final VerticalConveyorSubsystem m_verticalConveyor;

    public VerticalConveyorUpCommand(VerticalConveyorSubsystem verticalConveyorSubsystem) {
        this.m_verticalConveyor = verticalConveyorSubsystem;
        addRequirements(this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_verticalConveyor.forwardVerticalConveyorMotor();

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_verticalConveyor.stopVerticalConveyorMotor();

    }
}
