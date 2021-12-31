package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class FlapDown extends Command {

    private final Flap m_flap;

    public FlapDown(Flap flap) {
        m_flap = flap;
        requires(m_flap);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_flap.setTalon(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        /*if (Robot.flap.getTopLimitSwitch() == true)
            return true;
        else */
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_flap.setTalon(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
