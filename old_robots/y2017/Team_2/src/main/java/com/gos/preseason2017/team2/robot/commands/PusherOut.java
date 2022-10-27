package com.gos.preseason2017.team2.robot.commands;

import com.gos.preseason2017.team2.robot.subsystems.Manipulator;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 *
 */
public class PusherOut extends WaitCommand {

    private final Manipulator m_manipulator;

    public PusherOut(Manipulator manipulator) {
        super(.5);
        m_manipulator = manipulator;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        super.initialize();
        m_manipulator.pusherOut();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_manipulator.pusherIn();
    }


}
