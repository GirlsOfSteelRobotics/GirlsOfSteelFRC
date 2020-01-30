package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {

    private final CANSparkMax m_masterLeft;
    private final CANSparkMax m_followerLeft; // NOPMD

    private final CANSparkMax m_masterRight;
    private final CANSparkMax m_followerRight; // NOPMD

    private final CANEncoder m_rightEncoder;
    private final CANEncoder m_leftEncoder;

    private final PigeonIMU m_pigeon;
    
    private final DifferentialDrive m_drive;

    private final DifferentialDriveOdometry m_odometry;

    private final double[] m_angles = new double[3];


    public Chassis() {
        m_masterLeft = new CANSparkMax(Constants.DRIVE_LEFT_MASTER_SPARK, MotorType.kBrushless);
        m_followerLeft = new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, MotorType.kBrushless);

        m_masterRight = new CANSparkMax(Constants.DRIVE_RIGHT_MASTER_SPARK, MotorType.kBrushless);
        m_followerRight = new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, MotorType.kBrushless);

        m_rightEncoder = m_masterRight.getEncoder();
        m_leftEncoder = m_masterLeft.getEncoder();
        
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
        
        m_pigeon = new PigeonIMU(0);
        
        m_masterLeft.setIdleMode(IdleMode.kBrake);
        m_followerLeft.setIdleMode(IdleMode.kBrake);

        m_masterRight.setIdleMode(IdleMode.kBrake);
        m_followerRight.setIdleMode(IdleMode.kBrake);

        // inverted should be true for Laika
        // masterLeft.setInverted(true);
        // followerLeft.setInverted(true);

        // masterRight.setInverted(true);
        // followerRight.setInverted(true);
        
        m_followerLeft.follow(m_masterLeft, false);
        m_followerRight.follow(m_masterRight, false);
        
        m_drive = new DifferentialDrive(m_masterLeft, m_masterRight);
        m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(0.1);
        m_drive.setMaxOutput(0.8);
    }

    @Override
    public void periodic() {
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

        m_pigeon.getYawPitchRoll(m_angles);

        SmartDashboard.putNumber("x", getX());
        SmartDashboard.putNumber("y", getY());
        SmartDashboard.putNumber("yaw", getHeading());
        SmartDashboard.putNumber("right encoder", getM_rightEncoder());
        SmartDashboard.putNumber("left encoder", getM_leftEncoder());

    }
    
    public CANSparkMax getLeftSparkMax() {
        return m_masterLeft;
    }

    public CANSparkMax getRightSparkMax() {
        return m_masterRight;
    }

    public double getM_leftEncoder() {
        return m_rightEncoder.getPosition();
    }
        
    public double getM_rightEncoder() {
        return m_leftEncoder.getPosition();
    }

    public double getAverageEncoderDistance() {
        return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
    }

    public double getX() {
        return m_odometry.getPoseMeters().getTranslation().getX();
    }

    public double getY() {
        return m_odometry.getPoseMeters().getTranslation().getY();
    }

    public double getHeading() {
        return m_angles[0];
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }
    
    public void setSpeed(final double speed) {
        m_drive.arcadeDrive(speed, 0);
    }

    //command to rotate robot to align with target based on limelight value
    public void setSteer(double steer) {
        m_drive.arcadeDrive(0, steer);
    }

    public void setSpeedAndSteer(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }
    
    public void stop() {
        m_drive.stopMotor();
    }
}
