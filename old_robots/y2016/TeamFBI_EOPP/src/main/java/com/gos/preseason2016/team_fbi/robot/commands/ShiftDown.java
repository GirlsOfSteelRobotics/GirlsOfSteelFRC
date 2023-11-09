package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Shifters;

public class ShiftDown extends Command {

    private final Shifters m_shifters;

    public ShiftDown(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }


    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        m_shifters.shiftLeft(false);
        m_shifters.shiftRight(false);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub

    }



}
