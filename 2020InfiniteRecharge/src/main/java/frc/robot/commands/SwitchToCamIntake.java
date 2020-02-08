package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Camera;

public class SwitchToCamIntake extends CommandBase {

    private final Camera m_camera;

    public SwitchToCamIntake(Camera camera) {
        this.m_camera = camera;

        super.addRequirements(camera);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_camera.switchToCamIntake();

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
