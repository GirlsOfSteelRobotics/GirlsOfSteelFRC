package com.gos.preseason2016.team_squirtle.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Ramp;

/**
 *
 */
public class RampUp extends Command {

    private final Ramp m_ramp;

    public RampUp(Ramp ramp) {
        m_ramp = ramp;
        addRequirements(m_ramp);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_ramp.up();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
