package com.scra.mepi.rapid_react.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    private final NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
    private final NetworkTableEntry m_horizontalAngle = m_table.getEntry("tx");
    private final NetworkTableEntry m_verticalAngle = m_table.getEntry("ty");

    public double limelightDistance() {
        double targetOffsetAngleVertical = m_verticalAngle.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 25.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 37.4;

        // distance from the target to the floor
        double goalHeightInches = 104;

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngleVertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

        // calculate distance
        return (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
    }

    public double limelightAngle() {
        return m_horizontalAngle.getDouble(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("limelight dist (in)", limelightDistance());
    }
}
