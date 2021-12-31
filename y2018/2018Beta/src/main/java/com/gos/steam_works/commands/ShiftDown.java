package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftDown extends Command {

    private final Shifters m_shifters;

    public ShiftDown(Shifters shifters) {
        m_shifters = shifters;
        requires(m_shifters);
    }


    @Override
    protected void initialize() {
        m_shifters.shiftGear(Shifters.Speed.kLow);
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }


}
