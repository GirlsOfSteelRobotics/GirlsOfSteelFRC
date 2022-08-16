package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.steam_works.subsystems.Loader;

/**
 *
 */
public class LoadBall extends CommandBase {

    private final Loader m_loader;

    public LoadBall(Loader loader) {
        m_loader = loader;
        addRequirements(m_loader);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        System.out.println("LoadBall Initialized");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_loader.loadBall(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_loader.stopLoader();
        System.out.println("LoadBall Finished");
    }


}
