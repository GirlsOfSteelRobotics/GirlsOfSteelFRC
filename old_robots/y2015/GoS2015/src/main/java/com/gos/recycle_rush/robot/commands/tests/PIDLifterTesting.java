package com.gos.recycle_rush.robot.commands.tests;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.recycle_rush.robot.subsystems.Lifter;

/**
 *
 */
public class PIDLifterTesting extends Command {

    private final Lifter m_lifter;

    public PIDLifterTesting(Lifter lifter) {
        m_lifter = lifter;
        addRequirements(m_lifter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_lifter.tunePID();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_lifter.isAtPosition());
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_lifter.stop();
    }


}
