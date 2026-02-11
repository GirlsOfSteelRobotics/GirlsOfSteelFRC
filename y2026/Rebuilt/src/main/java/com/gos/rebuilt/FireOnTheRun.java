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

public class FireOnTheRun {

    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;
    private final StructPublisher<Translation3d> m_publisherTranslation;
    private final StructPublisher<Pose2d> m_publisherPose;
    private final ArrayList<StructPublisher<Pose3d>> m_hubList;
    private final ShooterSimBalls m_shooterSimBalls;
    private final ArrayList<ShooterSimBalls> m_shooterSimBallsList;

    public FireOnTheRun(ChassisSubsystem chassis, ShooterSubsystem shooter) {
        m_chassis = chassis;
        m_shooter = shooter;
        m_publisherTranslation = NetworkTableInstance.getDefault().getStructTopic("imaginary hub", Translation3d.struct).publish();
        m_publisherPose = NetworkTableInstance.getDefault().getStructTopic("robot pose", Pose2d.struct).publish();
        m_shooterSimBalls = new ShooterSimBalls("hypotheticalShootOnTheMove");
        m_hubList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            m_hubList.add(NetworkTableInstance.getDefault().getStructTopic("hub pose " + i, Pose3d.struct).publish());
        }
        m_shooterSimBallsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            m_shooterSimBallsList.add(new ShooterSimBalls("hypothetical Shoot On The Move " + i));
        }
    }

    public double getDistance(Translation3d point) {
        return m_chassis.getDistanceToObject(point.toTranslation2d());
    }

    public double getFuelVelocity(Translation3d point) {
        return m_shooter.rpmToVelocity(
            m_shooter.rpmFromDistance(getDistance(point))
        );
    }

    public double fuelAirTime(Translation3d point) {
        return getDistance(point) / (getFuelVelocity(point)
            * Math.cos(ShooterSubsystem.SHOT_ANGLE.getRadians()));
    }

    public Translation3d imaginaryHub(double time, ChassisSpeeds chassisSpeed) {
        double deltaX = time * chassisSpeed.vxMetersPerSecond;
        double deltaY = time * chassisSpeed.vyMetersPerSecond;

        return new Translation3d(
            Hub.innerCenterPoint.toTranslation2d().getX() - deltaX,
            Hub.innerCenterPoint.toTranslation2d().getY() - deltaY,
            Hub.height
        );
    }

    public Translation3d glue() {
        ChassisSpeeds robotVel = ChassisSpeeds.fromRobotRelativeSpeeds(m_chassis.getState().Speeds, m_chassis.getState().Pose.getRotation());
        ChassisSpeeds robotVelRob = ChassisSpeeds.fromFieldRelativeSpeeds(robotVel, m_chassis.getState().Pose.getRotation());


        Translation3d imaginaryPoint = imaginaryHub(fuelAirTime(Hub.innerCenterPoint), robotVel);
        Pose2d robotPose = new Pose2d();

        for (int i = 0; i < 10; i++) {

            m_publisherTranslation.accept(imaginaryPoint);
            imaginaryPoint = imaginaryHub(fuelAirTime(imaginaryPoint), robotVel);
            m_hubList.get(i).accept(new Pose3d(imaginaryPoint, new Rotation3d()));
            robotPose = new Pose2d(
                m_chassis.getState().Pose.getX(),
                m_chassis.getState().Pose.getY(),
                m_chassis.getFaceAngle(imaginaryPoint.toTranslation2d()));
            m_publisherPose.accept(robotPose);
            m_shooterSimBallsList.get(i).calculatePosition(getFuelVelocity(imaginaryPoint), robotVel, robotPose);
        }


        return imaginaryPoint;
    }


}
