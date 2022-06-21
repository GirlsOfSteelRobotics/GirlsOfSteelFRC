package com.gos.stronghold.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Claw;

/**
 *
 */
public class AutoReleaseBall extends CommandBase {

    private final Claw m_claw;
    private final Timer m_tim;
    private final double m_time;

    public AutoReleaseBall(Claw claw, double time) {
        m_claw = claw;
        addRequirements(m_claw);
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
        m_claw.collectRelease(.9);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_tim.get() >= m_time;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_claw.collectRelease(0);
    }


}
