package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Camera;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class SwitchToCamGear extends CommandBase {
    private final Camera m_camera;

    public SwitchToCamGear(Camera camera) {
        m_camera = camera;
        addRequirements(m_camera);
    }


    @Override
    public void initialize() {
        m_camera.switchToCamGear();
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }


}
