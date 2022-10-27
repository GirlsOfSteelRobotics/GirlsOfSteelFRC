package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class NudgeFlapUp extends CommandBase {
    private final Timer m_time = new Timer();
    private final Flap m_flap;

    public NudgeFlapUp(Flap flap) {
        m_flap = flap;
        addRequirements(m_flap);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_flap.setTalon(-.5);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_time.get() >= .5;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_flap.stopTalon();
    }


}
