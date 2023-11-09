package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class FlapUp extends Command {

    private final Flap m_flap;

    public FlapUp(Flap flap) {
        m_flap = flap;
        addRequirements(m_flap);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_flap.setTalon(-.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_flap.setTalon(0);
    }


}
