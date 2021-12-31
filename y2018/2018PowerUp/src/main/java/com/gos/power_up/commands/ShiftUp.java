package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Shifters;
import com.gos.power_up.subsystems.Shifters.Speed;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftUp extends Command {
    private final Shifters m_shifters;

    public ShiftUp(Shifters shifters) {
        m_shifters = shifters;
        requires(m_shifters);
    }


    @Override
    protected void initialize() {
        m_shifters.shiftGear(Speed.kHigh);

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
