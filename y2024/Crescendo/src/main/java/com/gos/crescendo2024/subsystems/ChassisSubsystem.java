// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.gos.crescendo2024.subsystems;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.crescendo2024.Constants;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.properties.pid.WpiProfiledPidPropertyBuilder;
import com.gos.lib.rev.swerve.RevSwerveChassis;
import com.gos.lib.rev.swerve.RevSwerveChassisConstants;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;

public class ChassisSubsystem extends SubsystemBase {
    private static final double WHEEL_BASE = 0.381;
    private static final double TRACK_WIDTH = 0.381;

    public static final double MAX_TRANSLATION_SPEED = Units.feetToMeters(13);
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(360);


    private final RevSwerveChassis m_swerveDrive;
    private final Pigeon2 m_gyro;

    private final Field2d m_field;

    private final ProfiledPIDController m_turnAnglePID;
    private final PidProperty m_turnAnglePIDProperties;

    @SuppressWarnings("PMD.UnnecessaryConstructor") // TODO remove
    public ChassisSubsystem() {
        m_gyro = new Pigeon2(Constants.PIGEON_PORT);
        m_gyro.getConfigurator().apply(new Pigeon2Configuration());

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            Constants.FRONT_LEFT_WHEEL, Constants.FRONT_LEFT_AZIMUTH,
            Constants.BACK_LEFT_WHEEL, Constants.BACK_LEFT_AZIMUTH,
            Constants.FRONT_RIGHT_WHEEL, Constants.FRONT_RIGHT_AZIMUTH,
            Constants.BACK_RIGHT_WHEEL, Constants.BACK_RIGHT_AZIMUTH,
            RevSwerveModuleConstants.DriveMotorTeeth.T14,
            WHEEL_BASE, TRACK_WIDTH,
            MAX_TRANSLATION_SPEED,
            MAX_ROTATION_SPEED);


        //TODO need change pls
        m_turnAnglePID = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_turnAnglePID.enableContinuousInput(0, 360);
        m_turnAnglePIDProperties = new WpiProfiledPidPropertyBuilder("Chassis to angle", false, m_turnAnglePID)
            .addP(0)
            .addI(0)
            .addD(0)
            .addMaxAcceleration(0)
            .addMaxVelocity(0)
            .build();


        m_swerveDrive = new RevSwerveChassis(swerveConstants, m_gyro::getRotation2d, new Pigeon2Wrapper(m_gyro));

        m_field = new Field2d();
        SmartDashboard.putData("Field", m_field);


    }

    @Override
    public void periodic() {
        m_swerveDrive.periodic();
        m_field.setRobotPose(m_swerveDrive.getEstimatedPosition());
        m_turnAnglePIDProperties.updateIfChanged();
    }


    @Override
    public void simulationPeriodic() {
        m_swerveDrive.updateSimulator();
    }

    public void teleopDrive(double xPercent, double yPercent, double rotPercent, boolean fieldRelative) {
        m_swerveDrive.driveWithJoysticks(xPercent, yPercent, rotPercent, fieldRelative);
    }

    public Pose2d getPose() {
        return m_swerveDrive.getEstimatedPosition();
    }

    public boolean turnPIDIsAngle() {
        return m_turnAnglePID.atGoal();
    }

    public void turnToAngle(double angleGoal) {
        double angleCurrentDegree = m_swerveDrive.getOdometryPosition().getRotation().getDegrees();
        double steerVoltage = m_turnAnglePID.calculate(angleCurrentDegree, angleGoal);
        //TODO add ff

        if (turnPIDIsAngle()) {
            steerVoltage = 0;
        }
        ChassisSpeeds speeds = new ChassisSpeeds(0, 0, steerVoltage);
        m_swerveDrive.setChassisSpeeds(speeds);
    }

    /////////////////////////////////////
    // Checklists
    /////////////////////////////////////


    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////


    public Command createTurnToAngleCommand(double angleGoal) {
        return runOnce(() -> m_turnAnglePID.reset(getPose().getRotation().getDegrees()))
            .andThen(this.run(() -> turnToAngle(angleGoal))
                .until(this::turnPIDIsAngle)
                .withName("Chassis to Angle" + angleGoal));
    }

}
