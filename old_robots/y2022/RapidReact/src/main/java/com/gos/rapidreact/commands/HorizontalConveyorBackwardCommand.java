package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class HorizontalConveyorBackwardCommand extends Command {
    private final HorizontalConveyorSubsystem m_horizontalConveyor;

    public HorizontalConveyorBackwardCommand(HorizontalConveyorSubsystem horizontalConveyorSubsystem) {
        this.m_horizontalConveyor = horizontalConveyorSubsystem;
        addRequirements(this.m_horizontalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_horizontalConveyor.backwardHorizontalConveyorMotor();

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_horizontalConveyor.stopHorizontalConveyorMotor();

    }
}
