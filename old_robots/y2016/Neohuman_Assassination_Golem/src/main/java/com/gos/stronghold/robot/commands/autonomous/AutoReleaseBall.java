package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Claw;

/**
 *
 */
public class AutoReleaseBall extends Command {

    private final Claw m_claw;
    private final Timer m_tim;
    private final double m_time;

    public AutoReleaseBall(Claw claw, double time) {
        m_claw = claw;
        requires(m_claw);
        m_time = time;
        m_tim = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_claw.collectRelease(.9);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_tim.get() >= m_time;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_claw.collectRelease(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
