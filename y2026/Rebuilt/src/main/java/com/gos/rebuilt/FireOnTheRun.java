package com.gos.rebuilt;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import org.littletonrobotics.frc2026.FieldConstants.Hub;

public class FireOnTheRun {

    private final ChassisSubsystem m_chassis;
    private final ShooterSubsystem m_shooter;
    private final StructPublisher<Translation3d> m_publisherTranslation;
    private final StructPublisher<Pose2d> m_publisherPose;
    private final ShooterSimBalls m_shooterSimBalls;

    public FireOnTheRun(ChassisSubsystem chassis, ShooterSubsystem shooter){
        m_chassis = chassis;
        m_shooter = shooter;
        m_publisherTranslation = NetworkTableInstance.getDefault().getStructTopic("imaginary hub", Translation3d.struct).publish();
        m_publisherPose = NetworkTableInstance.getDefault().getStructTopic("robot pose", Pose2d.struct).publish();
        m_shooterSimBalls = new ShooterSimBalls("hypotheticalShootOnTheMove");
    }

    public double getDistance(Translation3d point) {
        return m_chassis.getDistanceToObject(point.toTranslation2d());
    }

    public double getFuelVelocity(Translation3d point) {
        return m_shooter.RPMtoVelocity(
            m_shooter.RPMFromDistance(getDistance(point))
        );
    }

    public double fuelAirTime(Translation3d point) {
        return getDistance(point) / (getFuelVelocity(point) *
            Math.cos());
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

    private final GosDoubleProperty TEMP_ROBOT_X = new GosDoubleProperty(false, "TEMP ROBOT X", 0);
    private final GosDoubleProperty TEMP_ROBOT_Y = new GosDoubleProperty(false, "TEMP ROBOT Y", 0);
    private final GosDoubleProperty TEMP_ROBOT_OMEGA = new GosDoubleProperty(false, "TEMP ROBOT OMEGA", 0);

    public Pose2d glue() {
        //ChassisSpeeds robotVel =  ChassisSpeeds.fromRobotRelativeSpeeds(m_chassis.getState().Speeds, m_chassis.getState().Pose.getRotation());
        ChassisSpeeds robotVelField = new ChassisSpeeds(TEMP_ROBOT_X.getValue(), TEMP_ROBOT_Y.getValue(), TEMP_ROBOT_OMEGA.getValue());
        ChassisSpeeds robotVelRob = ChassisSpeeds.fromFieldRelativeSpeeds(robotVelField, m_chassis.getState().Pose.getRotation());
        Translation3d imaginaryPoint = imaginaryHub(fuelAirTime(Hub.innerCenterPoint), robotVelField);

        m_publisherTranslation.accept(imaginaryPoint);

        Pose2d robotPose = new Pose2d(
            m_chassis.getState().Pose.getX(),
            m_chassis.getState().Pose.getY(),
            m_chassis.getFaceAngle(imaginaryPoint.toTranslation2d()));

        m_publisherPose.accept(robotPose);
        m_shooterSimBalls.calculatePosition(getFuelVelocity(imaginaryPoint), robotVelField, robotPose);

        return robotPose;
    }




}
