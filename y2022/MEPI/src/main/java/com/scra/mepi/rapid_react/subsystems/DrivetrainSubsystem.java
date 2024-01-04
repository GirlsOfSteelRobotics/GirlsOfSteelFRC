// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.scra.mepi.rapid_react.subsystems;

import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkPIDController;
import com.scra.mepi.rapid_react.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.navx.NavxWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

// Drive train
@SuppressWarnings({"PMD.TooManyMethods", "PMD.TooManyFields"})
public class DrivetrainSubsystem extends SubsystemBase {
    private final SimableCANSparkMax m_leftLeader =
        new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER, MotorType.kBrushless);
    private final SimableCANSparkMax m_leftFollower =
        new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER, MotorType.kBrushless);
    private final SimableCANSparkMax m_rightLeader =
        new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER, MotorType.kBrushless);
    private final SimableCANSparkMax m_rightFollower =
        new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER, MotorType.kBrushless);
    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);
    private final DifferentialDriveKinematics m_kinematics =
        new DifferentialDriveKinematics(Constants.DRIVE_TRACK);

    // odometry
    private final RelativeEncoder m_leftEncoder = m_leftLeader.getEncoder();
    private final RelativeEncoder m_rightEncoder = m_rightLeader.getEncoder();
    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);
    private final DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
    private final Field2d m_field = new Field2d();

    // PID
    private final SparkPIDController m_leftController = m_leftLeader.getPIDController();
    private final PidProperty m_leftProperties;
    private final SparkPIDController m_rightController = m_rightLeader.getPIDController();
    private final PidProperty m_rightProperties;

    // Simulation
    private DifferentialDrivetrainSimWrapper m_simulator;

    /**
     * Creates a new DrivetrainSubsystem.
     */
    public DrivetrainSubsystem() {
        m_leftProperties = setupVelocityPidValues(m_leftController);
        m_rightProperties = setupVelocityPidValues(m_rightController);

        m_leftLeader.restoreFactoryDefaults();
        m_leftFollower.restoreFactoryDefaults();
        m_rightLeader.restoreFactoryDefaults();
        m_rightFollower.restoreFactoryDefaults();

        m_leftLeader.setSmartCurrentLimit(50);
        m_leftFollower.setSmartCurrentLimit(50);
        m_rightLeader.setSmartCurrentLimit(50);
        m_rightFollower.setSmartCurrentLimit(50);

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


        if (RobotBase.isSimulation()) {
            DifferentialDrivetrainSim drivetrainSim = DifferentialDrivetrainSim.createKitbotSim(
                DifferentialDrivetrainSim.KitbotMotor.kDoubleNEOPerSide,
                DifferentialDrivetrainSim.KitbotGearing.k5p95,
                DifferentialDrivetrainSim.KitbotWheelSize.kSixInch,
                null);
            m_simulator = new DifferentialDrivetrainSimWrapper(
                drivetrainSim,
                new RevMotorControllerSimWrapper(m_leftLeader),
                new RevMotorControllerSimWrapper(m_rightLeader),
                RevEncoderSimWrapper.create(m_leftLeader),
                RevEncoderSimWrapper.create(m_rightLeader),
                new NavxWrapper().getYawGyro());
            m_simulator.setRightInverted(false);
        }
    }

    private PidProperty setupVelocityPidValues(SparkPIDController pidController) {
        return new RevPidPropertyBuilder("ChassisVelocity", false, pidController, 0)
            .addP(1)
            .addI(0)
            .addD(0)
            .build();
    }

    public void control(double speed, double rotation) {
        m_drive.curvatureDrive(speed, rotation, Math.abs(speed) < 0.05);
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        m_leftController.setReference(leftVelocity, ControlType.kVelocity);
        m_rightController.setReference(rightVelocity, ControlType.kVelocity);
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

    public double leftVelocity() {
        return m_leftEncoder.getVelocity();
    }

    public double rightVelocity() {
        return m_rightEncoder.getVelocity();
    }

    public Command createPathFollowingCommand(String pathName) {
        return AutoBuilder.followPathWithEvents(PathPlannerPath.fromPathFile(pathName));
    }

    @Override
    public void periodic() {

        // This method will be called once per scheduler run
        var gyroAngle = Rotation2d.fromDegrees(-m_gyro.getAngle());
        m_odometry.update(gyroAngle, m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());
        SmartDashboard.putNumber("Chassis Left Velocity", leftVelocity());
        SmartDashboard.putNumber("Chassis Right Velocity", rightVelocity());

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
