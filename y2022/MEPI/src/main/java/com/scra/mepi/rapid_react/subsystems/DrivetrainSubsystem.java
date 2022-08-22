// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.scra.mepi.rapid_react.Constants;
import com.scra.mepi.rapid_react.TunableNumber;

// Drive train
public class DrivetrainSubsystem extends SubsystemBase {
    private CANSparkMax m_leftLeader =
            new CANSparkMax(Constants.DRIVE_LEFT_LEADER, MotorType.kBrushless);
    private CANSparkMax m_leftFollower =
            new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER, MotorType.kBrushless);
    private CANSparkMax m_rightLeader =
            new CANSparkMax(Constants.DRIVE_RIGHT_LEADER, MotorType.kBrushless);
    private CANSparkMax m_rightFollower =
            new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER, MotorType.kBrushless);
    private DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);
    private DifferentialDriveKinematics m_kinematics =
            new DifferentialDriveKinematics(Constants.DRIVE_TRACK);

    // odometry
    private RelativeEncoder m_leftEncoder = m_leftLeader.getEncoder();
    private RelativeEncoder m_rightEncoder = m_rightLeader.getEncoder();
    private AHRS m_gyro = new AHRS(SPI.Port.kMXP);
    private DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(new Rotation2d());
    private Field2d m_field = new Field2d();

    // PID
    private SparkMaxPIDController m_leftController = m_leftLeader.getPIDController();
    private SparkMaxPIDController m_rightController = m_rightLeader.getPIDController();
    private TunableNumber m_velocityP = new TunableNumber("Drive Train Velocity P", 1);
    private TunableNumber m_velocityI = new TunableNumber("Drive Train Velocity I");
    private TunableNumber m_velocityD = new TunableNumber("Drive Train Velocity D");

    /** Creates a new DrivetrainSubsystem. */
    public DrivetrainSubsystem() {
        m_gyro.calibrate();

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
        if (m_velocityP.hasChanged() || m_velocityI.hasChanged() || m_velocityD.hasChanged()) {
            updateDrivePID();
        }
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

    public void updateDrivePID() {
        m_leftController.setP(m_velocityP.get());
        m_leftController.setI(m_velocityI.get());
        m_leftController.setD(m_velocityD.get());
        m_rightController.setP(m_velocityP.get());
        m_rightController.setI(m_velocityI.get());
        m_rightController.setD(m_velocityD.get());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        var gyroAngle = Rotation2d.fromDegrees(-m_gyro.getAngle());
        m_odometry.update(gyroAngle, m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
