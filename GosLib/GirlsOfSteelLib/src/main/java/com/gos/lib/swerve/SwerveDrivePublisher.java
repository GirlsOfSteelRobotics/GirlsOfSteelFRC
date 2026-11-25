package com.gos.lib.swerve;

import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.SwerveModuleVelocity;
import org.wpilib.networktables.NetworkTable;
import org.wpilib.networktables.NetworkTableEntry;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.networktables.StructArrayPublisher;

public class SwerveDrivePublisher {
    private final StructArrayPublisher<SwerveModuleVelocity> m_measuredStatesEntry;
    private final StructArrayPublisher<SwerveModuleVelocity> m_desiredStatesEntry;
    private final NetworkTableEntry m_robotRotationEntry;

    public SwerveDrivePublisher() {
        this(NetworkTableInstance.getDefault().getTable("SwerveDrive"));
    }

    public SwerveDrivePublisher(NetworkTable table) {
        table.getEntry("rotationUnit").setString("degrees");
        m_measuredStatesEntry = table.getStructArrayTopic("MeasuredStates", SwerveModuleVelocity.struct).publish();
        m_desiredStatesEntry = table.getStructArrayTopic("DesiredStates", SwerveModuleVelocity.struct).publish();
        m_robotRotationEntry = table.getEntry("RobotRotation");
    }

    public void setMeasuredStates(SwerveModuleVelocity... states) {
        m_measuredStatesEntry.set(states);
    }

    public void setDesiredStates(SwerveModuleVelocity... states) {
        m_desiredStatesEntry.set(states);
    }

    public void setRobotRotation(Rotation2d rotation) {
        m_robotRotationEntry.setNumber(rotation.getDegrees());
    }
}
