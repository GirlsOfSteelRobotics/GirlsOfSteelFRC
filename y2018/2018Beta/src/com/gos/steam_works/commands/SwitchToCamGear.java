package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Camera;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchToCamGear extends Command {
    private final Camera m_camera;

    public SwitchToCamGear(Camera camera) {
        m_camera = camera;
        requires(m_camera);
    }


    @Override
    protected void initialize() {
        m_camera.switchToCamGear();
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }


}
