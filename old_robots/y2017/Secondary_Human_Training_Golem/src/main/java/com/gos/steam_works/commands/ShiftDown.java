package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shifters.Speed;

/**
 *
 */
public class ShiftDown extends Command {

    private final Shifters m_shifters;

    public ShiftDown(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_shifters.shiftGear(Speed.LOW);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
