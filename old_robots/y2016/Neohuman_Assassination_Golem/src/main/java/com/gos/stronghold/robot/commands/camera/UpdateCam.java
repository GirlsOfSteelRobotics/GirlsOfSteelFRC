package com.gos.stronghold.robot.commands.camera;

import edu.wpi.first.wpilibj2.command.Command;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class UpdateCam extends Command {

    private final Camera m_camera;

    public UpdateCam(Camera camera) {
        m_camera = camera;
        addRequirements(m_camera);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_camera.getImage();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
