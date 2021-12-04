package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.RobotBase;
import org.usfirst.frc.team3504.robot.RobotMap;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

    private final UsbCamera camGear;
    private final UsbCamera camClimb;
    public UsbCamera visionCam;
    private final MjpegServer server;


    public Camera() {
        camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
        camGear.setResolution(320, 240);
        camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
        camClimb.setResolution(320, 240);
        visionCam = new UsbCamera("visionCam", RobotMap.VISION_CAMERA);
        visionCam.setResolution(320, 240);
        visionCam.setExposureManual(16);

        if (RobotBase.isReal()) {
            camGear.setFPS(10);
            camClimb.setFPS(10);
            visionCam.setFPS(10);
        }

        CameraServer.getInstance().addCamera(camGear);
        CameraServer.getInstance().addCamera(camClimb);
        CameraServer.getInstance().addCamera(visionCam);
        server = CameraServer.getInstance().addServer("CameraServer", 1181);
        server.setSource(camGear);

        // For stream in smartdashboard add a mjpg stream viewer,
        // right click, select properties, and add
        // http://roborio-3504-frc.local:1181/stream.mjpg
        // as the URL
    }

    public void switchToCamClimb() {
        server.setSource(camClimb);
        System.out.println("Cam Climb!");
    }

    public void switchToCamGear() {
        server.setSource(camGear);
        System.out.println("Cam Gear!");
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }


}
