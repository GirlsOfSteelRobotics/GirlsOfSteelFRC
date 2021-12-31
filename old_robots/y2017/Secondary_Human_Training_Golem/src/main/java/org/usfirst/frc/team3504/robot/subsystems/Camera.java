package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.usfirst.frc.team3504.robot.GripPipeline;
import org.usfirst.frc.team3504.robot.GripPipelineListener;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
@SuppressWarnings("PMD.DoNotUseThreads")
public class Camera extends Subsystem {

    private final UsbCamera m_camGear;
    private final UsbCamera m_camClimb;
    private final UsbCamera m_visionCam;
    private final MjpegServer m_server;

    private final GripPipelineListener m_pipelineListener;
    private final VisionThread m_visionThread;
    private double m_targetX;
    private double m_height;


    public Camera() {
        m_camGear = new UsbCamera("camGear", RobotMap.CAMERA_GEAR);
        m_camGear.setResolution(320, 240);
        m_camClimb = new UsbCamera("camClimb", RobotMap.CAMERA_CLIMB);
        m_camClimb.setResolution(320, 240);
        m_visionCam = new UsbCamera("visionCam", RobotMap.VISION_CAMERA);
        m_visionCam.setResolution(320, 240);
        m_visionCam.setExposureManual(16);

        if (RobotBase.isReal()) {
            m_camGear.setFPS(10);
            m_camClimb.setFPS(10);
            m_visionCam.setFPS(10);
        }

        CameraServer.getInstance().addCamera(m_camGear);
        CameraServer.getInstance().addCamera(m_camClimb);
        CameraServer.getInstance().addCamera(m_visionCam);
        m_server = CameraServer.getInstance().addServer("CameraServer", 1181);
        m_server.setSource(m_camGear);

        // For stream in smartdashboard add a mjpg stream viewer,
        // right click, select properties, and add
        // http://roborio-3504-frc.local:1181/stream.mjpg
        // as the URL


        m_pipelineListener = new GripPipelineListener();

        m_visionThread = new VisionThread(m_visionCam, new GripPipeline(), m_pipelineListener);
        m_visionThread.start();
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
    }

    @Override
    public void periodic() {
        m_targetX = m_pipelineListener.getTargetX();
        m_height = m_pipelineListener.getHeight();

        SmartDashboard.putNumber("TargetX", m_targetX);
        SmartDashboard.putNumber("Height", m_height);
    }

    public double getTargetX() {
        return m_targetX;
    }

    public double getHeight() {
        return m_height;
    }


}
