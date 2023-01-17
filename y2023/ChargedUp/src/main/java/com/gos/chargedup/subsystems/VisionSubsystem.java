package com.gos.chargedup.subsystems;


import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.RobotPoseEstimator;

import java.util.ArrayList;
import java.util.Optional;


public class VisionSubsystem implements Subsystem {

    private PhotonCamera m_camera;

    private RobotPoseEstimator m_robotPoseEstimator;


    static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);
    static final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);
    // Angle between horizontal and the camera.
    static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

    //getDistanceAngle

    public VisionSubsystem() {
        m_camera = new PhotonCamera("photonvision");
        var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        m_robotPoseEstimator = new RobotPoseEstimator(null, RobotPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camList);

    }

    public Pair<Pose2d, Double> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_robotPoseEstimator.setReferencePose(prevEstimatedRobotPose);

        double currentTime = Timer.getFPGATimestamp();
        Optional<Pair<Pose3d, Double>> result = m_robotPoseEstimator.update();
        if (result.isPresent()) {
            return new Pair<Pose2d, Double>(
                result.get().getFirst().toPose2d(), currentTime - result.get().getSecond());
        } else {
            return new Pair<Pose2d, Double>(null, 0.0);
        }

    }




}

