package org.usfirst.frc.team3504.robot.commands.shack;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Shack;

/**
 *
 */
public class ShackOut extends Command {

    private final Shack m_shack;

    public ShackOut(Shack shack) {
        m_shack = shack;
        requires(m_shack);
    }

    @Override
    protected void initialize() {
        m_shack.shackOut();
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
