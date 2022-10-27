package com.gos.testboard2020.subsystems;

import com.gos.testboard2020.Constants;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Camera extends SubsystemBase {

    private final UsbCamera m_camIntake;
    private final UsbCamera m_camClimb;
    private final VideoSink m_server;

    public Camera() {
        if (RobotBase.isSimulation()) {
            m_camIntake = m_camClimb = null;
            m_server = null;
            return;
        }
        m_camIntake = new UsbCamera("camIntake", Constants.CAMERA_INTAKE);
        m_camIntake.setResolution(320, 240);
        m_camIntake.setFPS(20);
        m_camClimb = new UsbCamera("camClimb", Constants.CAMERA_CLIMB);
        m_camClimb.setResolution(320, 240);
        m_camClimb.setFPS(20);
        m_server = CameraServer.addSwitchedCamera("Driver Cameras");
        m_server.setSource(m_camIntake);

        // To see the stream in the Dashboard, add a CameraServer Stream Viewer
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if (RobotBase.isSimulation()) {
            return;
        }
    }

    public void switchToCamClimb() {
        if (RobotBase.isSimulation()) {
            return;
        }
        m_server.setSource(m_camClimb);
        System.out.println("Cam Climb!");
    }

    public void switchToCamIntake() {
        if (RobotBase.isSimulation()) {
            return;
        }

        m_server.setSource(m_camIntake);
        System.out.println("Cam Intake!");
    }
}
