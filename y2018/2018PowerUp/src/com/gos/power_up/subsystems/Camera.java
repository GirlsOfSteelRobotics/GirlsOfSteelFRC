package com.gos.power_up.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {

    private final UsbCamera m_driveCam;


    public Camera() {

        m_driveCam = CameraServer.getInstance().startAutomaticCapture();
        m_driveCam.setResolution(320, 240);
        m_driveCam.setFPS(20);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

}
