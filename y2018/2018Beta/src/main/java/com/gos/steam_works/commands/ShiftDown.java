package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class ShiftDown extends CommandBase {

    private final Shifters m_shifters;

    public ShiftDown(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }


    @Override
    public void initialize() {
        m_shifters.shiftGear(Shifters.Speed.kLow);
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
