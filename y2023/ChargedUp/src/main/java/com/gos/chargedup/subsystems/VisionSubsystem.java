package com.gos.chargedup.subsystems;


import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.RobotPoseEstimator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class VisionSubsystem implements Subsystem {

    private PhotonCamera m_camera;

    private RobotPoseEstimator m_robotPoseEstimator;


    static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(29);
    static final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);
    // Angle between horizontal and the camera.
    static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(30);

    //getDistanceAngle

    public VisionSubsystem() {
        AprilTagFieldLayout aprilTagFieldLayout;


        m_camera = new PhotonCamera("OV5647");
        var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
        try {
            aprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        camList.add(new Pair<>(m_camera, new Transform3d(new Translation3d(0, Units.inchesToMeters(16), Units.inchesToMeters(29)), new Rotation3d(0, CAMERA_PITCH_RADIANS, 0))));

        m_robotPoseEstimator = new RobotPoseEstimator(aprilTagFieldLayout, RobotPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camList);

    }

    public Pair<Pose2d, Double> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        m_robotPoseEstimator.setReferencePose(prevEstimatedRobotPose);

        double currentTime = Timer.getFPGATimestamp();
        Optional<Pair<Pose3d, Double>> result = m_robotPoseEstimator.update();
        if (result.isPresent()) {
            Pose3d pose = result.get().getFirst();
            double timestamp = result.get().getSecond();
            System.out.println("We got something...." + pose + ", " + timestamp);
            if (pose != null) {
                return new Pair<>(
                    pose.toPose2d(), currentTime - timestamp);
            }
        } else {
            System.out.println("Nothing found");
        }
//        System.out.println("result is present: " + result.isPresent() + "\nnot null: " + (result.get().getFirst() != null));
//        if (result.isPresent() && result.get().getFirst() != null) {
//            System.out.println(result.get().getFirst().toPose2d() + ", " + (currentTime - result.get().getSecond()));
//            return new Pair<Pose2d, Double>(
//                result.get().getFirst().toPose2d(), currentTime - result.get().getSecond());
//        } else {
//            return new Pair<Pose2d, Double>(null, 0.0);
//        }
        return new Pair<Pose2d, Double>(null, 0.0);

    }




}

