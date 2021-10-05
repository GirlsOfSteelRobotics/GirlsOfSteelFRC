package com.gos.power_up.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {

    private UsbCamera driveCam;


    public Camera() {

        driveCam = CameraServer.getInstance().startAutomaticCapture();
        driveCam.setResolution(320, 240);
        driveCam.setFPS(20);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

}
