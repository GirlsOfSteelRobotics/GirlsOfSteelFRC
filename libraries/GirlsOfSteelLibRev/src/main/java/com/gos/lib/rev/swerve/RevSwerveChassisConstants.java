package com.gos.lib.rev.swerve;

@SuppressWarnings("PMD.DataClass")
public class RevSwerveChassisConstants {
    // Angular offsets of the modules relative to the chassis in radians
    public static final double FRONT_LEFT_CHASSIS_ANGULAR_OFFSET = -Math.PI / 2;
    public static final double FRONT_RIGHT_CHASSIS_ANGULAR_OFFSET = 0;
    public static final double BACK_LEFT_CHASSIS_ANGULAR_OFFSET = Math.PI;
    public static final double BACK_RIGHT_CHASSIS_ANGULAR_OFFSET = Math.PI / 2;

    // SPARK MAX CAN IDs
    public final int m_frontLeftDrivingCanId;
    public final int m_rearLeftDrivingCanId;
    public final int m_frontRightDrivingCanId;
    public final int m_rearRightDrivingCanId;

    public final int m_frontLeftTurningCanId;
    public final int m_rearLeftTurningCanId;
    public final int m_frontRightTurningCanId;
    public final int m_rearRightTurningCanId;

    public final double m_wheelBase;
    public final double m_trackWidth;

    public final RevSwerveModuleConstants.DriveMotorTeeth m_moduleDriveTeeth;
    public final double m_maxSpeedMetersPerSecond;
    public final double m_maxAngularSpeed;

    /**
     * Constants for configuring a REV Swerve Chassis
     *
     * @param frontLeftDrivingCanId The CAN id for the front left driving motor
     * @param frontLeftTurningCanId The CAN id for the front left azimuth motor
     * @param rearLeftDrivingCanId The CAN id for the rear left driving motor
     * @param rearLeftTurningCanId The CAN id for the rear left azimuth motor
     * @param frontRightDrivingCanId The CAN id for the front right driving motor
     * @param frontRightTurningCanId The CAN id for the front right azimuth motor
     * @param rearRightDrivingCanId The CAN id for the front right driving motor
     * @param rearRightTurningCanId The CAN id for the front right azimuth motor
     * @param moduleDriveTeeth The number of teeth in the driving motor pinion
     * @param wheelBase Distance between front and back wheels on robot, in meters
     * @param trackWidth Distance between centers of right and left wheels on robot, in meters
     * @param maxSpeedMetersPerSecond The maximum translation speed of the drivetrain, in m / s
     * @param maxAngularSpeed The maximum turning speed of the drivetrain, in rad / s
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public RevSwerveChassisConstants(
        int frontLeftDrivingCanId,
        int frontLeftTurningCanId,

        int rearLeftDrivingCanId,
        int rearLeftTurningCanId,

        int frontRightDrivingCanId,
        int frontRightTurningCanId,

        int rearRightDrivingCanId,
        int rearRightTurningCanId,

        RevSwerveModuleConstants.DriveMotorTeeth moduleDriveTeeth,

        double wheelBase,
        double trackWidth,

        double maxSpeedMetersPerSecond,
        double maxAngularSpeed) {
        m_frontLeftDrivingCanId = frontLeftDrivingCanId;
        m_rearLeftDrivingCanId = rearLeftDrivingCanId;
        m_frontRightDrivingCanId = frontRightDrivingCanId;
        m_rearRightDrivingCanId = rearRightDrivingCanId;

        m_frontLeftTurningCanId = frontLeftTurningCanId;
        m_rearLeftTurningCanId = rearLeftTurningCanId;
        m_frontRightTurningCanId = frontRightTurningCanId;
        m_rearRightTurningCanId = rearRightTurningCanId;

        m_wheelBase = wheelBase;
        m_trackWidth = trackWidth;

        m_moduleDriveTeeth = moduleDriveTeeth;
        m_maxSpeedMetersPerSecond = maxSpeedMetersPerSecond;
        m_maxAngularSpeed = maxAngularSpeed;
    }
}
