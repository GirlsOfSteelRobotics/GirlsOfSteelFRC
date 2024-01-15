package com.gos.preseason2017.team1.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.preseason2017.team1.robot.subsystems.JawPiston;

/**
 *
 */
public class ShootPrep extends Command {

    private final JawPiston m_jaw;

    public ShootPrep(JawPiston jaw) {
        m_jaw = jaw;
        addRequirements(m_jaw);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_jaw.pistonsOut();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        //Robot.arm.armUp();

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
