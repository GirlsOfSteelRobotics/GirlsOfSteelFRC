package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

public class ShiftUp extends Command {

    private final Shifters m_shifters;

    public ShiftUp(Shifters shifters){
        m_shifters = shifters;
        requires(m_shifters);
    }

    @Override
    protected void initialize() {
        m_shifters.shiftLeft(true);
        m_shifters.shiftRight(true);
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
