package com.gos.reefscape.subsystems;


import com.ctre.phoenix6.hardware.Pigeon2;
import com.gos.reefscape.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.phoenix6.Pigeon2Wrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;
import org.snobotv2.sim_wrappers.SwerveSimWrapper;

import java.util.List;

public class REVchassisSubsystem extends SubsystemBase {
    public static final double WHEEL_BASE = Units.inchesToMeters(25);
    public static final double TRACK_WIDTH = Units.inchesToMeters(25);
    public static final double MAX_TRANSLATION_SPEED = Units.feetToMeters(20.1);
    public static final double MAX_ROTATION_SPEED = Units.degreesToRadians(540);
    private final RevSwerveModule m_backLeft;
    private final RevSwerveModule m_backRight;
    private final RevSwerveModule m_frontLeft;
    private final RevSwerveModule m_frontRight;
    private final SwerveDriveOdometry m_odometry;


    private final Pigeon2 m_gyro;

    private final RevSwerveModule[] m_modules;

    private final SwerveDriveKinematics m_kinematics;
    private final Field2d m_field;


    // Simulation
    private SwerveSimWrapper m_simulator;

    public REVchassisSubsystem() {
        m_backLeft = new RevSwerveModule(Constants.BACK_LEFT_CANCODER_ID, Constants. BACK_LEFT_DRIVE_MOTOR_ID, Constants.BACK_LEFT_STEER_MOTOR_ID);
        m_backRight = new RevSwerveModule(Constants.BACK_RIGHT_CANCODER_ID, Constants.BACK_RIGHT_DRIVE_MOTOR_ID, Constants. BACK_RIGHT_STEER_MOTOR_ID);
        m_frontLeft = new RevSwerveModule(Constants. FRONT_LEFT_CANCODER_ID, Constants.FRONT_LEFT_DRIVE_MOTOR_ID, Constants.FRONT_LEFT_STEER_MOTOR_ID);
        m_frontRight = new RevSwerveModule(Constants. FRONT_RIGHT_CANCODER_ID, Constants.FRONT_RIGHT_DRIVE_MOTOR_ID, Constants. FRONT_RIGHT_STEER_MOTOR_ID);

        m_gyro = new Pigeon2(Constants.PIGEON_ID);

        m_modules = new RevSwerveModule[]{m_frontLeft, m_frontRight, m_backLeft, m_backRight};

        m_kinematics = new SwerveDriveKinematics(
            new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2),
            new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2),
            new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2),
            new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)
        );

        m_odometry = new SwerveDriveOdometry(
            m_kinematics, m_gyro.getRotation2d(),
            new SwerveModulePosition[] {
                m_frontLeft.getPosition(),
                m_frontRight.getPosition(),
                m_backLeft.getPosition(),
                m_backRight.getPosition()
            }, new Pose2d(0, 0, new Rotation2d()));

        if (RobotBase.isSimulation()) {
            List<SwerveModuleSimWrapper> moduleSims = List.of(
                m_frontLeft.getSimWrapper(),
                m_frontRight.getSimWrapper(),
                m_backLeft.getSimWrapper(),
                m_backRight.getSimWrapper());
            m_simulator = new SwerveSimWrapper(WHEEL_BASE, TRACK_WIDTH, 64.0, 1.0, moduleSims, new Pigeon2Wrapper(m_gyro));
        }
        m_field = new Field2d();
        SmartDashboard.putData(m_field);
    }

    public void setChassisSpeed(ChassisSpeeds chassisSpeed) {
        System.out.println(chassisSpeed);
        SwerveModuleState[] moduleStates = m_kinematics.toSwerveModuleStates(chassisSpeed);
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_TRANSLATION_SPEED);
        setModuleStates(moduleStates);
    }

    public void setModuleStates(SwerveModuleState... desiredStates) {
        for (int i = 0; i < 4; i++) {
            m_modules[i].  drive(desiredStates[i].speedMetersPerSecond, desiredStates[i].angle.getDegrees());

        }
    }

    public void swerveDrive(double xVelocity, double yVelocity, double rotationalVelocity) {
        ChassisSpeeds chassisSpeeds = new ChassisSpeeds(xVelocity, yVelocity, rotationalVelocity);
        setChassisSpeed(chassisSpeeds);
    }

    @Override
    public void periodic() {
        var gyroAngle = m_gyro.getRotation2d();
        // Update the pose
        m_odometry.update(gyroAngle,
            new SwerveModulePosition[] {
                m_frontLeft.getPosition(), m_frontRight.getPosition(),
                m_backLeft.getPosition(), m_backRight.getPosition()
            });
        m_field.setRobotPose(m_odometry.getPoseMeters());

        m_backLeft.periodic();
        m_backRight.periodic();
        m_frontRight.periodic();
        m_frontLeft.periodic();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}

