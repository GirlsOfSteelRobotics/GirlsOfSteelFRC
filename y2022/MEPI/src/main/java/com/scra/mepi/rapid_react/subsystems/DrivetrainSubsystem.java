// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Drive train
@SuppressWarnings("PMD.TooManyFields")
public class DrivetrainSubsystem extends SubsystemBase {
    private final CANSparkMax m_leftLeader =
        new CANSparkMax(Constants.DRIVE_LEFT_LEADER, MotorType.kBrushless);
    private final CANSparkMax m_leftFollower =
        new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER, MotorType.kBrushless);
    private final CANSparkMax m_rightLeader =
        new CANSparkMax(Constants.DRIVE_RIGHT_LEADER, MotorType.kBrushless);
    private final CANSparkMax m_rightFollower =
        new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER, MotorType.kBrushless);
    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);
    private final DifferentialDriveKinematics m_kinematics =
        new DifferentialDriveKinematics(Constants.DRIVE_TRACK);

    // odometry
    private final RelativeEncoder m_leftEncoder = m_leftLeader.getEncoder();
    private final RelativeEncoder m_rightEncoder = m_rightLeader.getEncoder();
    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);
    private final DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(new Rotation2d());
    private final Field2d m_field = new Field2d();

    // PID
    private final SparkMaxPIDController m_leftController = m_leftLeader.getPIDController();
    private final PidProperty m_leftProperties;
    private final SparkMaxPIDController m_rightController = m_rightLeader.getPIDController();
    private final PidProperty m_rightProperties;

    /**
     * Creates a new DrivetrainSubsystem.
     */
    public DrivetrainSubsystem() {
        m_gyro.calibrate();

        m_leftProperties = setupVelocityPidValues(m_leftController);
        m_rightProperties = setupVelocityPidValues(m_rightController);

        m_leftLeader.restoreFactoryDefaults();
        m_leftFollower.restoreFactoryDefaults();
        m_rightLeader.restoreFactoryDefaults();
        m_rightFollower.restoreFactoryDefaults();

        m_leftLeader.setInverted(true);

        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);

        m_leftEncoder.setPositionConversionFactor(Constants.DRIVE_CONVERSION_FACTOR);
        m_rightEncoder.setPositionConversionFactor(Constants.DRIVE_CONVERSION_FACTOR);
        m_leftEncoder.setVelocityConversionFactor(Constants.DRIVE_CONVERSION_FACTOR);
        m_rightEncoder.setVelocityConversionFactor(Constants.DRIVE_CONVERSION_FACTOR);

        m_leftLeader.burnFlash();
        m_leftFollower.burnFlash();
        m_rightLeader.burnFlash();
        m_rightFollower.burnFlash();

        SmartDashboard.putData(m_field);
    }

    private PidProperty setupVelocityPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("ChassisVelocity", false, pidController, 0)
            .addP(1)
            .addI(0)
            .addD(0)
            .build();
    }

    public void control(double speed, double rotation) {
        m_drive.curvatureDrive(speed, rotation, Math.abs(speed) < 0.05);
    }

    public void applyChassisSpeed(ChassisSpeeds speeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);
        applyWheelSpeed(wheelSpeeds);
    }

    public void applyWheelSpeed(DifferentialDriveWheelSpeeds speeds) {
        m_leftController.setReference(speeds.leftMetersPerSecond, ControlType.kSmartVelocity);
        m_rightController.setReference(speeds.rightMetersPerSecond, ControlType.kSmartVelocity);
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        var gyroAngle = Rotation2d.fromDegrees(-m_gyro.getAngle());
        m_odometry.update(gyroAngle, m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
