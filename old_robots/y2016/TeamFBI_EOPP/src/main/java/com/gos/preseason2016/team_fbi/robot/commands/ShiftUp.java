package com.gos.preseason2016.team_fbi.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_fbi.robot.subsystems.Shifters;

public class ShiftUp extends Command {

    private final Shifters m_shifters;

    public ShiftUp(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }

    @Override
    public void initialize() {
        m_shifters.shiftLeft(true);
        m_shifters.shiftRight(true);
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
