package com.gos.rebuilt;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import org.littletonrobotics.frc2026.FieldConstants.Hub;

import java.util.ArrayList;
import java.util.List;

public class FireOnTheRun {

    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;
    private final StructPublisher<Pose2d> m_publisherPose;
    private final List<StructPublisher<Pose3d>> m_hubList;
    private final List<ShooterSimBalls> m_shooterSimBallsList;
    private static final double ITERATIONS = 10;

    public FireOnTheRun(ChassisSubsystem chassis, ShooterSubsystem shooter) {
        m_chassis = chassis;
        m_shooter = shooter;
        m_publisherPose = NetworkTableInstance.getDefault().getStructTopic("FOTR/robot pose", Pose2d.struct).publish();
        m_hubList = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            m_hubList.add(NetworkTableInstance.getDefault().getStructTopic("FOTR/hub pose " + i, Pose3d.struct).publish());
        }
        m_shooterSimBallsList = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            m_shooterSimBallsList.add(new ShooterSimBalls("FOTR/hypothetical Shoot On The Move " + i));
        }
    }

    public double getDistance(Translation3d point) {
        Pose2d robotPosition = m_chassis.getState().Pose;
        Pose2d shooterPose = new Pose2d(robotPosition.getTranslation().minus(ShooterSubsystem.SHOOTER_OFFSET.rotateBy(robotPosition.getRotation())), robotPosition.getRotation());
        double distanceX = shooterPose.getX() - point.getX();
        double distanceY = shooterPose.getY() - point.getY();

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public double getFuelVelocity(Translation3d point) {
        return m_shooter.rpmToVelocity(m_shooter.rpmFromDistance(getDistance(point)));
    }

    public double fuelAirTime(Translation3d point) {
        return getDistance(point) / (getFuelVelocity(point) * Math.cos(ShooterSubsystem.SHOT_ANGLE.getRadians()));
    }

    public Translation3d imaginaryHub(double time, ChassisSpeeds chassisSpeed) {
        double deltaX = time * chassisSpeed.vxMetersPerSecond;
        double deltaY = time * chassisSpeed.vyMetersPerSecond;

        return new Translation3d(Hub.innerCenterPoint.toTranslation2d().getX() - deltaX, Hub.innerCenterPoint.toTranslation2d().getY() - deltaY, Hub.height);
    }

    public Translation3d findImaginary() {
        ChassisSpeeds robotVel = ChassisSpeeds.fromRobotRelativeSpeeds(m_chassis.getState().Speeds, m_chassis.getState().Pose.getRotation());


        Translation3d imaginaryPoint = Hub.innerCenterPoint;
        Pose2d robotPose = new Pose2d();

        for (int i = 0; i < ITERATIONS; i++) {
            imaginaryPoint = imaginaryHub(fuelAirTime(imaginaryPoint), robotVel);
            m_hubList.get(i).accept(new Pose3d(imaginaryPoint, new Rotation3d()));
            robotPose = new Pose2d(m_chassis.getState().Pose.getX(), m_chassis.getState().Pose.getY(), m_chassis.getShooterFaceAngle(imaginaryPoint.toTranslation2d()));
            m_shooterSimBallsList.get(i).calculatePosition(getFuelVelocity(imaginaryPoint), robotVel, robotPose);
        }
        m_publisherPose.accept(robotPose);


        return imaginaryPoint;
    }


}
