package com.gos.rebuilt;


//imports

import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;

import java.util.ArrayList;

public class ShooterSimBalls {
    //variables lol
    private double m_fuelInitVX;
    private double m_fuelInitVY;
    private double m_fuelInitVZ;
    private Pose2d m_robotPosition;
    private final double m_height;
    private final Rotation2d m_theta = ShooterSubsystem.SHOT_ANGLE;
    private final StructArrayPublisher<Translation3d> m_publisher;

    //constructor
    public ShooterSimBalls(String name) {
        m_height = Units.inchesToMeters(20);
        m_theta = Rotation2d.fromDegrees(60);
        m_publisher = NetworkTableInstance.getDefault().getStructArrayTopic(name, Translation3d.struct).publish();
    }

    public double calculatePosX(double time) {
        return m_fuelInitVX * time + m_robotPosition.getX();
    }

    public double calculatePosZ(double time) {
        return m_fuelInitVZ * time + 0.5 * (-9.8) * time * time + m_height;
    }

    public double calculatePosY(double time) {
        return m_fuelInitVY * time + m_robotPosition.getY();
    }

    public void calculatePosition(double launchSpeed, ChassisSpeeds fieldVelocity, Pose2d robotPosition) {
        m_robotPosition = robotPosition;
        m_fuelInitVZ = launchSpeed * Math.sin(m_theta.getRadians());
        double horizComp = launchSpeed * Math.cos(m_theta.getRadians());
        m_fuelInitVY = -Math.sin(robotPosition.getRotation().getRadians()) * horizComp + fieldVelocity.vyMetersPerSecond;
        m_fuelInitVX = -Math.cos(robotPosition.getRotation().getRadians()) * horizComp + fieldVelocity.vxMetersPerSecond;


        ArrayList<Translation3d> listOfTrans = new ArrayList<>(10);
        for (double t = 0; t <= 5; t += .2) {
            Translation3d trans = new Translation3d(calculatePosX(t), calculatePosY(t), calculatePosZ(t));
            listOfTrans.add(trans);
            if (calculatePosZ(t) < 0) {
                break;
            }
        }

        m_publisher.accept(listOfTrans.toArray(new Translation3d[] {}));
    }

}

