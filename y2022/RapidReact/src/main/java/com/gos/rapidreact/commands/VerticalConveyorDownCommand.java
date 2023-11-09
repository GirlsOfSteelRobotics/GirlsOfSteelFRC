package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;


public class VerticalConveyorDownCommand extends Command {
    private final VerticalConveyorSubsystem m_verticalConveyor;

    public VerticalConveyorDownCommand(VerticalConveyorSubsystem verticalConveyorSubsystem) {
        this.m_verticalConveyor = verticalConveyorSubsystem;
        addRequirements(this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_verticalConveyor.backwardVerticalConveyorMotor();

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
