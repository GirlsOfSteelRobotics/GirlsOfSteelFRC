package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;


public class AutomatedVerticalConveyorCommand extends CommandBase {
    private final VerticalConveyorSubsystem m_verticalConveyor;

    public AutomatedVerticalConveyorCommand(VerticalConveyorSubsystem verticalConveyorSubsystem) {
        this.m_verticalConveyor = verticalConveyorSubsystem;

        addRequirements(this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // at sensor 1, run conveyor
        if (m_verticalConveyor.getUpperBeamBreak() && !m_verticalConveyor.getLowerBeamBreak()) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
        }

        if (m_verticalConveyor.getLowerBeamBreak() && !m_verticalConveyor.getUpperBeamBreak()) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
        }

        if (m_verticalConveyor.getUpperBeamBreak() && m_verticalConveyor.getLowerBeamBreak()) {
            m_verticalConveyor.stopVerticalConveyorMotor();
        }



        // between sensors 1 and 2, stop conveyor
        // at sensor 2, run conveyor feeder


    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
