package com.gos.preseason2017.team1.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2017.team1.robot.subsystems.Shifters;
import com.gos.preseason2017.team1.robot.subsystems.Shifters.Speed;

/**
 *
 */
public class ShiftUp extends Command {
    private final Shifters m_shifters;

    public ShiftUp(Shifters shifters) {
        m_shifters = shifters;
        addRequirements(m_shifters);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_shifters.shiftLeft(Speed.HIGH);
        m_shifters.shiftRight(Speed.HIGH);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        // The solenoid setting commands should complete immediately
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
