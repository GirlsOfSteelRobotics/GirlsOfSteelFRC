package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Loader;

/**
 *
 */
public class LoadBall extends Command {

    private final Loader m_loader;

    public LoadBall(Loader loader) {
        m_loader = loader;
        requires(m_loader);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("LoadBall Initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_loader.loadBall(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_loader.stopLoader();
        System.out.println("LoadBall Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
