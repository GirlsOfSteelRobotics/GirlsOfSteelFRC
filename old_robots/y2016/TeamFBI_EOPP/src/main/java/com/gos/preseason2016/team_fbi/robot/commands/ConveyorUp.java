package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Manipulator;

public class ConveyorUp extends Command {

    private final Manipulator m_manipulator;

    public ConveyorUp(Manipulator manipulator) {
        m_manipulator = manipulator;
        addRequirements(m_manipulator);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_manipulator.manipulatorConveyorBeltMotorLeft(true);
        m_manipulator.manipulatorConveyorBeltMotorRight(true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_manipulator.stopConveyorBeltMotor();
    }


}
