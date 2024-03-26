package com.gos.crescendo2024;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class RobotExtrinsics {
    // Robot Size
    public static final double ROBOT_WIDTH;
    public static final double ROBOT_LENGTH;

    static {
        if (Constants.IS_COMPETITION_ROBOT) {
            ROBOT_WIDTH = Units.inchesToMeters(25);
            ROBOT_LENGTH = Units.inchesToMeters(25);
        } else {
            ROBOT_WIDTH = Units.inchesToMeters(28);
            ROBOT_LENGTH = Units.inchesToMeters(28);
        }

    }

    // Camera Extrinsics
    public static final Transform3d ROBOT_TO_CAM_OBJET_DETECTION = new Transform3d(
        new Translation3d(
            0,
            0,
            .20), // 20cm off the ground
        new Rotation3d(
            Math.toRadians(0),
            Math.toRadians(11),
            Math.toRadians(3)));

    public static final Transform3d ROBOT_TO_CAMERA_APRIL_TAGS_R = new Transform3d(
        new Translation3d(
            0,
            0,
            .235),
        new Rotation3d(0, Math.toRadians(0), Math.toRadians(-90))
    );
    public static final Transform3d ROBOT_TO_CAMERA_APRIL_TAGS_L = new Transform3d(
        new Translation3d(
            0,
            0,
            .235),
        new Rotation3d(0, Math.toRadians(0), Math.toRadians(90))
    );
    public static final Transform3d ROBOT_TO_CAMERA_APRIL_TAGS_CB;


    // Important Robot Poses
    public static final Pose2d STARTING_POSE_AMP_SUBWOOFER = new Pose2d(0.6933452953924437, 6.686887667641241, Rotation2d.fromDegrees(60));
    public static final Pose2d STARTING_POSE_MIDDLE_SUBWOOFER = new Pose2d(1.34, 5.55, Rotation2d.fromDegrees(0));
    public static final Pose2d STARTING_POSE_SOURCE_SUBWOOFER = new Pose2d(0.6933452953924437, 4.403274541213847, Rotation2d.fromDegrees(-60));
    public static final Pose2d SCORE_IN_AMP_POSITION = new Pose2d(1.84, 7.8, Rotation2d.fromDegrees(90));

    // Important Aiming Poses
    public static final Pose2d FULL_FIELD_FEEDING_AIMING_POINT = new Pose2d(FieldConstants.AMP_CENTER, Rotation2d.fromDegrees(0));

    static {
        if (Constants.IS_COMPETITION_ROBOT) {
            ROBOT_TO_CAMERA_APRIL_TAGS_CB = new Transform3d(
                new Translation3d(
                    -(RobotExtrinsics.ROBOT_WIDTH / 2 - Units.inchesToMeters(2.5)), // 2.5 inches from back
                    0,
                    .235),
                new Rotation3d(0, Math.toRadians(-40), Math.toRadians(180))
            );
        } else {
            ROBOT_TO_CAMERA_APRIL_TAGS_CB = new Transform3d(
                new Translation3d(
                    -(RobotExtrinsics.ROBOT_WIDTH / 2 - 0.04), // 4cm from back
                    -(RobotExtrinsics.ROBOT_LENGTH / 2 - .42), // 27cm from right side - changed to .42 out of guess and check (2/19)
                    .235),
                new Rotation3d(0, Math.toRadians(-34), Math.toRadians(178))
            );
        }
    }
}
