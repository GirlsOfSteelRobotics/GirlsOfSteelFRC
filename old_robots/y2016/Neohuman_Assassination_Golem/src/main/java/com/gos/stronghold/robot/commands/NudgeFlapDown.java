package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class NudgeFlapDown extends Command {
    private final Timer m_time = new Timer();
    private final Flap m_flap;

    public NudgeFlapDown(Flap flap) {
        m_flap = flap;
        requires(m_flap);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_flap.setTalon(.5);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_time.get() >= .5;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_flap.stopTalon();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
