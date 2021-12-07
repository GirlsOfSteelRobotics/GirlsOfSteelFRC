package org.usfirst.frc.team3504.robot.commands.tests;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;

/**
 *
 */
public class PIDLifterTesting extends Command {

    private final Lifter m_lifter;

    public PIDLifterTesting(Lifter lifter) {
        m_lifter = lifter;
        requires(m_lifter);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_lifter.tunePID();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return (m_lifter.isAtPosition());
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_lifter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
