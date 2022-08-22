package com.scra.mepi.rapid_react.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry m_horizontalAngle = table.getEntry("tx");
    NetworkTableEntry m_verticalAngle = table.getEntry("ty");
    NetworkTableEntry m_targetArea = table.getEntry("ta");

    public LimelightSubsystem() {}

    public double limelightDistance() {
        double targetOffsetAngle_Vertical = m_verticalAngle.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 25.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 37.4;

        // distance from the target to the floor
        double goalHeightInches = 104;

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
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
