package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Loader;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LoadBall extends Command {
    private final Loader m_loader;

    public LoadBall(Loader loader) {
        m_loader = loader;
        requires(m_loader);
    }


    @Override
    protected void initialize() {
        System.out.println("LoadBall Initialized");
    }


    @Override
    protected void execute() {
        m_loader.loadBall(-1.0);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_loader.stopLoader();
        System.out.println("LoadBall Finished");
    }
}
