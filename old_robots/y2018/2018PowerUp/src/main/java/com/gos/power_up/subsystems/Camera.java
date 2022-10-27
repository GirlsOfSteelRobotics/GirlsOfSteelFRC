package com.gos.power_up.subsystems;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {

    private final UsbCamera m_driveCam;


    public Camera() {

        m_driveCam = CameraServer.startAutomaticCapture();
        m_driveCam.setResolution(320, 240);
        m_driveCam.setFPS(20);
    }



}
