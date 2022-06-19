package com.gos.stronghold.robot.commands.camera;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.stronghold.robot.subsystems.Camera;

/**
 *
 */
public class SwitchCam extends CommandBase {

    private final Camera m_camera;

    public SwitchCam(Camera camera) {
        m_camera = camera;
        addRequirements(m_camera);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_camera.switchCam();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }


}
