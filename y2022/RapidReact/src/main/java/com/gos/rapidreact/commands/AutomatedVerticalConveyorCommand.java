package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;


public class AutomatedVerticalConveyorCommand extends CommandBase {
    private final VerticalConveyorSubsystem m_verticalConveyor;
    private final HorizontalConveyorSubsystem m_horizontalConveyor;

    public AutomatedVerticalConveyorCommand(VerticalConveyorSubsystem verticalConveyorSubsystem, HorizontalConveyorSubsystem horizontalConveyorSubsystem) {
        this.m_verticalConveyor = verticalConveyorSubsystem;
        this.m_horizontalConveyor = horizontalConveyorSubsystem;

        addRequirements(this.m_verticalConveyor);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // at sensor 1, run conveyor
        m_horizontalConveyor.forwardHorizontalConveyorMotor();
        if (m_verticalConveyor.getUpperBeamBreak() && !m_verticalConveyor.getLowerBeamBreak()) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
            System.out.println(m_verticalConveyor.getVerticalConveyorSpeed());
        }

        if (m_verticalConveyor.getLowerBeamBreak() && !m_verticalConveyor.getUpperBeamBreak()) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
            System.out.println(m_verticalConveyor.getVerticalConveyorSpeed());
        }

        if (m_verticalConveyor.getUpperBeamBreak() && m_verticalConveyor.getLowerBeamBreak()) {
            m_verticalConveyor.stopVerticalConveyorMotor();
            System.out.println(m_verticalConveyor.getVerticalConveyorSpeed());
        }

        if (!(m_verticalConveyor.getLowerBeamBreak() && m_verticalConveyor.getUpperBeamBreak())) {
            m_verticalConveyor.forwardVerticalConveyorMotor();
            System.out.println(m_verticalConveyor.getVerticalConveyorSpeed());
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
        m_verticalConveyor.stopVerticalConveyorMotor();
    }
}
