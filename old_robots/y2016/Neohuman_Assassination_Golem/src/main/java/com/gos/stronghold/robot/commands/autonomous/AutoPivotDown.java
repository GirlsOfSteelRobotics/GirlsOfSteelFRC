package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Pivot;

/**
 *
 */
public class AutoPivotDown extends CommandBase {

    private final Pivot m_pivot;
    private final Timer m_tim;
    private final double m_time;

    public AutoPivotDown(Pivot pivot, double time) {
        m_pivot = pivot;
        addRequirements(m_pivot);
        m_time = time;
        m_tim = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_pivot.tiltUpandDown(-0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_tim.get() >= m_time;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_pivot.tiltUpandDown(0);
    }


}
