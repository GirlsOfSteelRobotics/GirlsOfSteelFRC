package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;

/**
 *
 */
public class AutoPivotDown extends Command {

    private final Pivot m_pivot;
    private final Timer m_tim;
    private final double m_time;

    public AutoPivotDown(Pivot pivot, double time) {
        m_pivot = pivot;
        requires(m_pivot);
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
        m_pivot.tiltUpandDown(-0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_tim.get() >= m_time;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_pivot.tiltUpandDown(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
