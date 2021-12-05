package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {

    private final CameraServer server;

    public Camera() {
        server = CameraServer.getInstance();
        //server.setQuality(50);
        // the camera name (ex "cam0") can be found through the roborio web
        // interface
        //server.startAutomaticCapture("cam0");
    }

    @Override
    protected void initDefaultCommand() {
    }
}
