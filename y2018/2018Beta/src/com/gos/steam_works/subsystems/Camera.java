package com.gos.steam_works.subsystems;

import com.gos.steam_works.RobotMap;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

    private final UsbCamera m_camGear;
    private final UsbCamera m_camClimb;
    private final UsbCamera m_visionCam;
    private final MjpegServer m_server;


    public Camera() {
        m_camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
        m_camGear.setResolution(320, 240);
        m_camGear.setFPS(10);
        m_camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
        m_camClimb.setResolution(320, 240);
        m_camClimb.setFPS(10);
        m_visionCam = new UsbCamera("visionCam", RobotMap.VISION_CAMERA);
        m_visionCam.setResolution(320, 240);
        m_visionCam.setFPS(10);
        m_visionCam.setExposureManual(16);
        CameraServer.getInstance().addCamera(m_camGear);
        CameraServer.getInstance().addCamera(m_camClimb);
        CameraServer.getInstance().addCamera(m_visionCam);
        m_server = CameraServer.getInstance().addServer("CameraServer", 1181);
        m_server.setSource(m_camGear);

        // For stream in smartdashboard add a mjpg stream viewer,
        // right click, select properties, and add
        // http://roborio-3504-frc.local:1181/stream.mjpg
        // as the URL
    }

    public void switchToCamClimb() {
        m_server.setSource(m_camClimb);
        System.out.println("Cam Climb!");
    }

    public void switchToCamGear() {
        m_server.setSource(m_camGear);
        System.out.println("Cam Gear!");
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }


    public VideoCamera getVisionCamera() {
        return m_visionCam;
    }
}
