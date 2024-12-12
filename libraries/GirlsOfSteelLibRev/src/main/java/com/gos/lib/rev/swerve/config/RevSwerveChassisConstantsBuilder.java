package com.gos.lib.rev.swerve.config;

import com.gos.lib.rev.RevMotorControllerModel;
import com.gos.lib.rev.RevMotorModel;
import com.gos.lib.rev.swerve.config.RevSwerveModuleConstants.DrivingClosedLoopParameters;
import com.gos.lib.rev.swerve.config.RevSwerveModuleConstants.TurningClosedLoopParameters;

public class RevSwerveChassisConstantsBuilder {

    // SPARK MAX CAN IDs
    private Integer m_frontLeftDrivingCanId;
    private Integer m_rearLeftDrivingCanId;
    private Integer m_frontRightDrivingCanId;
    private Integer m_rearRightDrivingCanId;

    private Integer m_frontLeftTurningCanId;
    private Integer m_rearLeftTurningCanId;
    private Integer m_frontRightTurningCanId;
    private Integer m_rearRightTurningCanId;

    private RevMotorModel m_driveMotorModel;
    private RevMotorControllerModel m_driveMotorControllerModel;
    private DriveMotorPinionTeeth m_moduleDrivePinionTeeth;
    private DriveMotorSpurTeeth m_moduleDriveSpurTeeth;

    private Double m_wheelBase;
    private Double m_trackWidth;

    private Double m_maxSpeedMetersPerSecond;
    private Double m_maxAngularSpeed;

    private DrivingClosedLoopParameters m_drivingClosedLoopParameters;
    private TurningClosedLoopParameters m_turningClosedLoopParameters;


    public RevSwerveChassisConstantsBuilder withFrontLeftConfig(int drivingCanId, int turningCanId) {
        m_frontLeftDrivingCanId = drivingCanId;
        m_frontLeftTurningCanId = turningCanId;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withFrontRightConfig(int drivingCanId, int turningCanId) {
        m_frontRightDrivingCanId = drivingCanId;
        m_frontRightTurningCanId = turningCanId;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withRearLeftConfig(int drivingCanId, int turningCanId) {
        m_rearLeftDrivingCanId = drivingCanId;
        m_rearLeftTurningCanId = turningCanId;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withRearRightConfig(int drivingCanId, int turningCanId) {
        m_rearRightDrivingCanId = drivingCanId;
        m_rearRightTurningCanId = turningCanId;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withDrivingMotorType(RevMotorModel motorModel, RevMotorControllerModel controllerModel) {
        m_driveMotorModel = motorModel;
        m_driveMotorControllerModel = controllerModel;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withTrackwidth(double trackWidth) {
        m_trackWidth = trackWidth;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withWheelBase(double wheelBase) {
        m_wheelBase = wheelBase;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withMaxTranslationSpeed(double speed) {
        m_maxSpeedMetersPerSecond = speed;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withMaxRotationSpeed(double speed) {
        m_maxAngularSpeed = speed;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withGearing(DriveMotorPinionTeeth pinionTeeth, DriveMotorSpurTeeth spurTeeth) {
        if (m_moduleDrivePinionTeeth != null || m_moduleDriveSpurTeeth != null) {
            throw new IllegalStateException("Gearing parameters have already been set...");
        }
        m_moduleDrivePinionTeeth = pinionTeeth;
        m_moduleDriveSpurTeeth = spurTeeth;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withGearing(SwerveGearingKit swerveGearing) {
        if (m_moduleDrivePinionTeeth != null || m_moduleDriveSpurTeeth != null) {
            throw new IllegalStateException("Gearing parameters have already been set...");
        }
        m_moduleDrivePinionTeeth = swerveGearing.m_pinionTeeth;
        m_moduleDriveSpurTeeth = swerveGearing.m_spurTeeth;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withDriveClosedLoopParams(DrivingClosedLoopParameters params) {
        m_drivingClosedLoopParameters = params;
        return this;
    }

    public RevSwerveChassisConstantsBuilder withTurningClosedLoopParams(TurningClosedLoopParameters params) {
        m_turningClosedLoopParameters = params;
        return this;
    }

    public RevSwerveChassisConstants build(boolean lockPidConstants) {

        RevSwerveModuleConstants moduleConstants = new RevSwerveModuleConstants(
            m_driveMotorControllerModel,
            m_driveMotorModel,
            m_moduleDrivePinionTeeth,
            m_moduleDriveSpurTeeth);

        if (m_drivingClosedLoopParameters == null) {
            m_drivingClosedLoopParameters = new DrivingClosedLoopParameters(
                0.04,
                0,
                1 / moduleConstants.m_driveWheelFreeSpeedRps
            );
        }
        if (m_turningClosedLoopParameters == null) {
            m_turningClosedLoopParameters = new TurningClosedLoopParameters(
                1, 0
            );
        }

        return new RevSwerveChassisConstants(
            m_frontLeftDrivingCanId, m_frontLeftTurningCanId,
            m_rearLeftDrivingCanId, m_rearLeftTurningCanId,
            m_frontRightDrivingCanId, m_frontRightTurningCanId,
            m_rearRightDrivingCanId, m_rearRightTurningCanId,
            m_driveMotorModel, m_driveMotorControllerModel,
            m_moduleDrivePinionTeeth,
            m_moduleDriveSpurTeeth,
            m_wheelBase,
            m_trackWidth,
            m_maxSpeedMetersPerSecond, m_maxAngularSpeed,
            m_drivingClosedLoopParameters, m_turningClosedLoopParameters,
            lockPidConstants,
            moduleConstants);
    }


}
