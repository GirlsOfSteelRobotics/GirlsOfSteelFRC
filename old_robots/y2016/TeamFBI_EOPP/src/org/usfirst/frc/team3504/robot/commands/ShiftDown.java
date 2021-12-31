package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

public class ShiftDown extends Command {

    private final Shifters m_shifters;

    public ShiftDown(Shifters shifters) {
        m_shifters = shifters;
        requires(m_shifters);
    }


    @Override
    protected void initialize() {
        // TODO Auto-generated method stub
        m_shifters.shiftLeft(false);
        m_shifters.shiftRight(false);
    }

    @Override
    protected void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void interrupted() {
        end();
        // TODO Auto-generated method stub

    }

}
