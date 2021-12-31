package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.JawPiston;

/**
 *
 */
public class ShootPrep extends Command {

    private final JawPiston m_jaw;

    public ShootPrep(JawPiston jaw) {
        m_jaw = jaw;
        requires(m_jaw);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_jaw.pistonsOut();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Robot.arm.armUp();

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
