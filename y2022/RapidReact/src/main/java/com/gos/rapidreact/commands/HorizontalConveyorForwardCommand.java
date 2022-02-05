package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;


public class HorizontalConveyorForwardCommand extends CommandBase {
    private final HorizontalConveyorSubsystem m_horizontalConveyor;

    public HorizontalConveyorForwardCommand(HorizontalConveyorSubsystem horizontalConveyorSubsystem) {
        this.m_horizontalConveyor = horizontalConveyorSubsystem;
        addRequirements(this.m_horizontalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_horizontalConveyor.forwardHorizontalConveyorMotor();

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
