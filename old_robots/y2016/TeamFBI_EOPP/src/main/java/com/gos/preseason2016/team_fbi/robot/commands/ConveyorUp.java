package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Manipulator;

public class ConveyorUp extends Command {

    private final Manipulator m_manipulator;

    public ConveyorUp(Manipulator manipulator) {
        m_manipulator = manipulator;
        requires(m_manipulator);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_manipulator.manipulatorConveyorBeltMotorLeft(true);
        m_manipulator.manipulatorConveyorBeltMotorRight(true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_manipulator.stopConveyorBeltMotor();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
