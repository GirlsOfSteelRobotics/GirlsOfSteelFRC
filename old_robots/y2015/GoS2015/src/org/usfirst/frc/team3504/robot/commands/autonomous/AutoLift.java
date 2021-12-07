package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;

/**
 *
 */
public class AutoLift extends Command {

    private final Lifter m_lifter;
    private final double m_distance;

    public AutoLift(Lifter lifter, double distance) {
        m_lifter = lifter;
        requires(m_lifter);
        this.m_distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_lifter.setPosition(m_distance);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_lifter.isAtPosition();
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
