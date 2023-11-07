package com.gos.lib.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SwerveDrivePublisher {
    private final NetworkTableEntry m_measuredStatesEntry;
    private final NetworkTableEntry m_desiredStatesEntry;
    private final NetworkTableEntry m_robotRotationEntry;

    private final Number[] m_measuredStates;
    private final Number[] m_desiredStates;

    public SwerveDrivePublisher() {
        this(NetworkTableInstance.getDefault().getTable("SwerveDrive"));
    }

    public SwerveDrivePublisher(NetworkTable table) {
        table.getEntry("rotationUnit").setString("degrees");
        m_measuredStatesEntry = table.getEntry("MeasuredStates");
        m_desiredStatesEntry = table.getEntry("DesiredStates");
        m_robotRotationEntry = table.getEntry("RobotRotation");

        m_measuredStates = new Number[4 * 2];
        m_desiredStates = new Number[4 * 2];
    }

    public void setMeasuredStates(SwerveModuleState... states) {
        for (int i = 0; i < states.length; ++i) {
            m_measuredStates[i * 2] = states[i].angle.getDegrees();
            m_measuredStates[i * 2 + 1] = states[i].speedMetersPerSecond;
        }
        m_measuredStatesEntry.setNumberArray(m_measuredStates);
    }

    public void setDesiredStates(SwerveModuleState... states) {
        for (int i = 0; i < states.length; ++i) {
            m_desiredStates[i * 2] = states[i].angle.getDegrees();
            m_desiredStates[i * 2 + 1] = states[i].speedMetersPerSecond;
        }
        m_desiredStatesEntry.setNumberArray(m_desiredStates);
    }

    public void setRobotRotation(Rotation2d rotation) {
        m_robotRotationEntry.setNumber(rotation.getDegrees());
    }
}
