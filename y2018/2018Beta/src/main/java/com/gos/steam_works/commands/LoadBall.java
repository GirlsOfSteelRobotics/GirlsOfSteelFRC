package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Loader;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class LoadBall extends CommandBase {
    private final Loader m_loader;

    public LoadBall(Loader loader) {
        m_loader = loader;
        addRequirements(m_loader);
    }


    @Override
    public void initialize() {
        System.out.println("LoadBall Initialized");
    }


    @Override
    public void execute() {
        m_loader.loadBall(-1.0);
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_loader.stopLoader();
        System.out.println("LoadBall Finished");
    }
}
