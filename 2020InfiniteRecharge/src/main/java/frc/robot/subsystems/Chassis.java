package frc.robot.subsystems;

//import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.IGyroWrapper;
import frc.robot.lib.MotionMagicProperty;
import frc.robot.lib.NavXWrapper;

import com.revrobotics.CANPIDController;

public class Chassis extends SubsystemBase {

    private static final double FULL_THROTTLE_SECONDS = 1.0;
    private static final double WHEEL_DIAMETER = 4.0;
    private static final double GEAR_RATIO = 40.0 / 10.0 * 34.0 / 20.0;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private final CANSparkMax m_masterLeft;
    private final CANSparkMax m_followerLeft; // NOPMD

    private final CANSparkMax m_masterRight;
    private final CANSparkMax m_followerRight; // NOPMD

    private final CANEncoder m_rightEncoder;
    private final CANEncoder m_leftEncoder;

    private final CANPIDController m_leftPidController;
    private final CANPIDController m_rightPidController;

    private final IGyroWrapper m_gyro;

    private final DifferentialDrive m_drive;

    private final DifferentialDriveOdometry m_odometry;

    private final NetworkTable m_customNetworkTable;

    private int m_robotPositionCtr; // Used for downsampling the updates

    private final MotionMagicProperty m_leftProperties;
    private final MotionMagicProperty m_rightProperties;

    public Chassis() {
        m_masterLeft = new CANSparkMax(Constants.DRIVE_LEFT_MASTER_SPARK, MotorType.kBrushless);
        m_followerLeft = new CANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, MotorType.kBrushless);
        m_masterRight = new CANSparkMax(Constants.DRIVE_RIGHT_MASTER_SPARK, MotorType.kBrushless);
        m_followerRight = new CANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, MotorType.kBrushless);

        m_masterLeft.restoreFactoryDefaults();
        m_followerLeft.restoreFactoryDefaults();
        m_masterRight.restoreFactoryDefaults();
        m_followerRight.restoreFactoryDefaults();

        m_rightEncoder = m_masterRight.getEncoder();
        m_leftEncoder = m_masterLeft.getEncoder();

        m_leftPidController = m_masterLeft.getPIDController();
        m_rightPidController = m_masterRight.getPIDController();

        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);

        m_leftEncoder.setPositionConversionFactor(ENCODER_CONSTANT);
        m_rightEncoder.setPositionConversionFactor(ENCODER_CONSTANT);

        m_leftEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);
        m_rightEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));

        m_gyro = new NavXWrapper(); 

        IdleMode idleMode = IdleMode.kBrake;
        m_masterLeft.setIdleMode(idleMode);
        m_followerLeft.setIdleMode(idleMode);
        m_masterRight.setIdleMode(idleMode);
        m_followerRight.setIdleMode(idleMode);

        m_masterLeft.setInverted(false);
        m_masterRight.setInverted(true);

        m_followerLeft.follow(m_masterLeft, false);
        m_followerRight.follow(m_masterRight, false);

        m_masterLeft.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_followerLeft.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_masterRight.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        m_followerRight.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
        
        // m_masterLeft.setOpenLoopRampRate(FULL_THROTTLE_SECONDS);
        // m_masterRight.setOpenLoopRampRate(FULL_THROTTLE_SECONDS);

        m_drive = new DifferentialDrive(m_masterLeft, m_masterRight);
        m_drive.setRightSideInverted(false);
        m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(0.1);
        m_drive.setMaxOutput(0.8);

        NetworkTable coordinateGuiContainer = NetworkTableInstance.getDefault().getTable("CoordinateGui");
        coordinateGuiContainer.getEntry(".type").setString("CoordinateGui");

        m_customNetworkTable = coordinateGuiContainer.getSubTable("RobotPosition");

        // Smart Motion stuff
        double kp = 0.008000;
        double ki = 0;
        double kd = 0;
        double kff = 0.005900;
        boolean lockConstants = false;
        double minVel = 0; // inch/sec
        double maxVel = 72; // inch/sec
        double maxAcc = 144; // inch/sec/sec
        double allowedErr = 0;
        double kMaxOutput = 1;
        double kMinOutput = -1;
        int smartMotionSlot = 0;

        m_leftProperties = MotionMagicProperty.Builder.createBuilder("Chassis")
            .addP(lockConstants, kp, m_leftPidController::setP)
            .addI(lockConstants, ki, m_leftPidController::setI)
            .addD(lockConstants, kd, m_leftPidController::setD)
            .addFF(lockConstants, kff, m_leftPidController::setFF)
            .addMaxVelocity(lockConstants, maxVel, (double value) -> m_leftPidController.setSmartMotionMaxVelocity(value, 0))
            .addMaxAcceleration(lockConstants, maxAcc, (double value) -> m_leftPidController.setSmartMotionMaxAccel(value, 0))
            .build();
        m_rightProperties = MotionMagicProperty.Builder.createBuilder("Chassis")
            .addP(lockConstants, kp, m_rightPidController::setP)
            .addI(lockConstants, ki, m_rightPidController::setI)
            .addD(lockConstants, kd, m_rightPidController::setD)
            .addFF(lockConstants, kff, m_rightPidController::setFF)
            .addMaxVelocity(lockConstants, maxVel, (double value) -> m_rightPidController.setSmartMotionMaxVelocity(value, 0))
            .addMaxAcceleration(lockConstants, maxAcc, (double value) -> m_rightPidController.setSmartMotionMaxAccel(value, 0))
            .build();

        m_leftPidController.setOutputRange(kMinOutput, kMaxOutput);
        m_rightPidController.setOutputRange(kMinOutput, kMaxOutput);
        m_leftPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        m_rightPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        m_leftPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        
        m_leftProperties.updateIfChanged(true);
        m_rightProperties.updateIfChanged(true);
    }

    @Override
    public void periodic() {
        m_gyro.poll();
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(),
                m_rightEncoder.getPosition());

        SmartDashboard.putNumber("x", getX());
        SmartDashboard.putNumber("y", getY());
        SmartDashboard.putNumber("yaw", getHeading());
        SmartDashboard.putNumber("right encoder", getM_rightEncoder());
        SmartDashboard.putNumber("left encoder", getM_leftEncoder());
        SmartDashboard.putNumber("right encoder speed", getM_rightEncoderSpeed());
        SmartDashboard.putNumber("left encoder speed", getM_leftEncoderSpeed());

        m_customNetworkTable.getEntry("X").setDouble(getX());
        m_customNetworkTable.getEntry("Y").setDouble(getY());
        m_customNetworkTable.getEntry("Angle").setDouble(getHeading());

        // Actually update the display every 5 loops = 100ms
        if (m_robotPositionCtr % 5 == 0) {
            m_customNetworkTable.getEntry("Ctr").setDouble(m_robotPositionCtr);
        }
        ++m_robotPositionCtr;

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
    }

    //////////////////////////////
    // Odometry Stuff
    //////////////////////////////
    public double getM_leftEncoder() {
        return m_rightEncoder.getPosition();
    }

    public double getM_rightEncoder() {
        return m_leftEncoder.getPosition();
    }

    public double getM_leftEncoderSpeed() {
        return m_rightEncoder.getVelocity();
    }

    public double getM_rightEncoderSpeed() {
        return m_leftEncoder.getVelocity();
    }

    public void setPosition(double x, double y, double angle) {
        m_gyro.setYaw(angle);
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        Rotation2d rotation = Rotation2d.fromDegrees(angle);
        m_odometry.resetPosition(new Pose2d(new Translation2d(x, y), rotation), rotation);
    }

    public double getX() {
        return m_odometry.getPoseMeters().getTranslation().getX();
    }

    public double getY() {
        return m_odometry.getPoseMeters().getTranslation().getY();
    }

    public double getHeading() {
        //return m_gyro.getYaw();
        return 0;
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public double getAverageEncoderDistance() {
        return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
    }

    //////////////////////////////
    // Functions to make it drive
    //////////////////////////////
    public void setSpeed(final double speed) {
        // System.out.println("SETTING SPEED");
        m_drive.arcadeDrive(speed, 0);
    }

    // command to rotate robot to align with target based on limelight value
    public void setSteer(double steer) {
        m_drive.arcadeDrive(0, steer);
        // System.out.println("SETTING STEER");
    }

    public void setSpeedAndSteer(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
        // System.out.println("SETTING SPEED AND STEER");
    }

    public void driveDistance(double leftPosition, double rightPosition) {
        m_drive.setSafetyEnabled(false);
        m_leftPidController.setReference(leftPosition, ControlType.kSmartMotion);
        m_rightPidController.setReference(rightPosition, ControlType.kSmartMotion);
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        m_drive.setSafetyEnabled(false);
        // System.out.println("Driving velocity");
        m_leftPidController.setReference(leftVelocity, ControlType.kVelocity);
        m_rightPidController.setReference(rightVelocity, ControlType.kVelocity);
    }

    public void stop() {
        m_drive.stopMotor();
        System.out.println("Stopping motors");
    }
}
