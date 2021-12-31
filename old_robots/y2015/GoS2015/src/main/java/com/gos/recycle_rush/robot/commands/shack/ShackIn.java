package com.gos.recycle_rush.robot.commands.shack;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 *
 */
public class ShackIn extends Command {

    private final Shack m_shack;

    public ShackIn(Shack shack) {
        m_shack = shack;
        requires(m_shack);
    }

    @Override
    protected void initialize() {
        m_shack.shackIn();
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

    @Override
    protected void interrupted() {
        end();
    }
}
