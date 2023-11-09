package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Shifters;

/**
 *
 */
public class ShiftLowGear extends Command {

    private final Shifters m_shifters;

    public ShiftLowGear(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_shifters.shiftLeft(false);
        m_shifters.shiftRight(false);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
