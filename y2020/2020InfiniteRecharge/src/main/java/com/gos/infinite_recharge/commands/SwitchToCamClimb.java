package com.gos.infinite_recharge.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.infinite_recharge.subsystems.Camera;

public class SwitchToCamClimb extends CommandBase {

    private final Camera m_camera;

    public SwitchToCamClimb(Camera camera) {
        this.m_camera = camera;

        super.addRequirements(camera);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Camera selection", "Climb");
    }

    @Override
    public void execute() {
        m_camera.switchToCamClimb();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
