package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;


public class BackwardHorizontalConveyorCommand extends CommandBase {
    private final HorizontalConveyorSubsystem m_horizontalConveyor;

    public BackwardHorizontalConveyorCommand(HorizontalConveyorSubsystem horizontalConveyorSubsystem) {
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
