package com.gos.rebuilt;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Pose3d;
import org.wpilib.math.geometry.Rotation3d;
import org.wpilib.math.geometry.Translation3d;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.networktables.StructPublisher;
import org.littletonrobotics.frc2026.FieldConstants.Hub;

import java.util.ArrayList;
import java.util.List;

public class FireOnTheRun {

    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;
    private final StructPublisher<Pose2d> m_publisherPose;
    private final StructPublisher<Translation3d> m_goalPosition;
    private final List<StructPublisher<Pose3d>> m_hubList;
    private final List<ShooterSimBalls> m_shooterSimBallsList;
    private static final double ITERATIONS = 10;
    private Translation3d m_goal;

    public FireOnTheRun(ChassisSubsystem chassis, ShooterSubsystem shooter) {
        m_chassis = chassis;
        m_shooter = shooter;
        m_publisherPose = NetworkTableInstance.getDefault().getStructTopic("FOTR/robot pose", Pose2d.struct).publish();
        m_goalPosition = NetworkTableInstance.getDefault().getStructTopic("FOTR/goal", Translation3d.struct).publish();
        m_hubList = new ArrayList<>();
        m_shooterSimBallsList = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            m_hubList.add(NetworkTableInstance.getDefault().getStructTopic("FOTR/hub pose " + i, Pose3d.struct).publish());
            m_shooterSimBallsList.add(new ShooterSimBalls("FOTR/hypothetical Shoot On The Move " + i));
        }
    }

    public double getFuelVelocity(Translation3d point) {
        return m_shooter.rpmToVelocity(m_shooter.rpmFromDistance(m_chassis.getDistanceToObject(point.toTranslation2d())));
    }

    public double fuelAirTime(Translation3d point) {
        return m_chassis.getDistanceToObject(point.toTranslation2d()) / (getFuelVelocity(point) * Math.cos(ShooterSubsystem.SHOT_ANGLE.getRadians()));
    }

    public Translation3d imaginaryHub(double time, ChassisVelocities chassisSpeed) {
        double deltaX = time * chassisSpeed.vx;
        double deltaY = time * chassisSpeed.vy;

        return new Translation3d(m_goal.getX() - deltaX, m_goal.getY() - deltaY, m_goal.getZ());
    }

    public Translation3d findImaginary(Translation3d goal) {
        m_goal = goal;
        m_goalPosition.accept(m_goal);
        ChassisVelocities robotVel = m_chassis.getState().Velocity.toFieldRelative(m_chassis.getState().Pose.getRotation());


        Translation3d imaginaryPoint = Hub.innerCenterPoint.getTranslation();
        Pose2d robotPose = new Pose2d();

        for (int i = 0; i < ITERATIONS; i++) {
            imaginaryPoint = imaginaryHub(fuelAirTime(imaginaryPoint), robotVel);
            robotPose = new Pose2d(m_chassis.getState().Pose.getX(), m_chassis.getState().Pose.getY(), m_chassis.getShooterFaceAngle(imaginaryPoint.toTranslation2d()));
            m_hubList.get(i).accept(new Pose3d(imaginaryPoint, new Rotation3d()));
            m_shooterSimBallsList.get(i).calculatePosition(getFuelVelocity(imaginaryPoint), robotVel, robotPose);
        }
        m_publisherPose.accept(robotPose);


        return imaginaryPoint;
    }


}
