package com.gos.chargedup.subsystems;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.PhotonCamera;

public class VisionSubsystem implements Subsystem {

    PhotonCamera m_camera;
    static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);
    static final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);
    // Angle between horizontal and the camera.
    static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

    //getDistanceAngle

    public VisionSubsystem() {
        m_camera = new PhotonCamera("photonvision");

    }

    public Pose2d getPosition() {
        //ToDo: Not this
        return null;
    }


}

