package com.gos.lib.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;

public class SwerveDrivePublisher {
    private final StructArrayPublisher<SwerveModuleState> m_measuredStatesEntry;
    private final StructArrayPublisher<SwerveModuleState> m_desiredStatesEntry;
    private final NetworkTableEntry m_robotRotationEntry;

    public SwerveDrivePublisher() {
        this(NetworkTableInstance.getDefault().getTable("SwerveDrive"));
    }

    public SwerveDrivePublisher(NetworkTable table) {
        table.getEntry("rotationUnit").setString("degrees");
        m_measuredStatesEntry = table.getStructArrayTopic("MeasuredStates", SwerveModuleState.struct).publish();
        m_desiredStatesEntry = table.getStructArrayTopic("DesiredStates", SwerveModuleState.struct).publish();
        m_robotRotationEntry = table.getEntry("RobotRotation");
    }

    public void setMeasuredStates(SwerveModuleState... states) {
        m_measuredStatesEntry.set(states);
    }

    public void setDesiredStates(SwerveModuleState... states) {
        m_desiredStatesEntry.set(states);
    }

    public void setRobotRotation(Rotation2d rotation) {
        m_robotRotationEntry.setNumber(rotation.getDegrees());
    }
}
