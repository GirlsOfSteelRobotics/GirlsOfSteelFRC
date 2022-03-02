package com.gos.preseason2017.team2.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.preseason2017.team2.robot.subsystems.Manipulator;

/**
 *
 */
public class PusherOut extends CommandBase {

    private final Manipulator m_manipulator;

    public PusherOut(Manipulator manipulator) {
        m_manipulator = manipulator;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        setTimeout(.5);
        m_manipulator.pusherOut();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_manipulator.pusherIn();
    }


}
