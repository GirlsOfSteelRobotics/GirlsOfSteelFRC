package com.gos.infinite_recharge.subsystems;

import com.gos.infinite_recharge.Constants;
import com.gos.infinite_recharge.Constants.DriveConstants;
import com.gos.infinite_recharge.sim.CameraSimulator;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;

import org.snobotv2.coordinate_gui.RobotPositionPublisher;
import org.snobotv2.module_wrappers.navx.NavxWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.numbers.N2;
import com.gos.lib.sensors.IGyroWrapper;
import com.gos.lib.navx.NavXWrapper;

import com.revrobotics.SparkMaxPIDController;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PMD.TooManyFields")
public class Chassis extends SubsystemBase {

    //private static final double FULL_THROTTLE_SECONDS = 1.0;
    private static final double WHEEL_DIAMETER = 4.0;
    private static final double GEAR_RATIO = 40.0 / 10.0 * 34.0 / 20.0;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private final SimableCANSparkMax m_masterLeft;
    private final SimableCANSparkMax m_followerLeft; // NOPMD

    private final SimableCANSparkMax m_masterRight;
    private final SimableCANSparkMax m_followerRight; // NOPMD

    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;

    private final SparkMaxPIDController m_leftPidController;
    private final SparkMaxPIDController m_rightPidController;

    private final IGyroWrapper m_gyro;

    private final DifferentialDrive m_drive;

    private final DifferentialDriveOdometry m_odometry;
    private final List<Field2d> m_fields;

    private final RobotPositionPublisher m_coordinateGuiPublisher;

    private final PidProperty m_leftProperties;
    private final PidProperty m_rightProperties;

    private DifferentialDrivetrainSimWrapper m_simulator;
    private CameraSimulator m_cameraSimulator;

    @SuppressWarnings({"PMD.NcssCount", "PMD.ExcessiveMethodLength"})
    public Chassis() {
        m_masterLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_MASTER_SPARK, MotorType.kBrushless);
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, MotorType.kBrushless);
        m_masterRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_MASTER_SPARK, MotorType.kBrushless);
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, MotorType.kBrushless);

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

        IdleMode idleMode = IdleMode.kCoast;
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
        m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(0.1);
        m_drive.setMaxOutput(0.8);

        m_coordinateGuiPublisher = new RobotPositionPublisher();

        // Smart Motion stuff
        double kp = 0.01000;
        double ki = 0;
        double kd = 0;
        double kff = 0.005800;
        boolean lockConstants = false;
        double minVel = 0; // inch/sec
        double maxVel = 72; // inch/sec
        double maxAcc = 144; // inch/sec/sec
        double allowedErr = 0;
        double kMaxOutput = 1;
        double kMinOutput = -1;
        int smartMotionSlot = 0;

        m_leftProperties = new RevPidPropertyBuilder("Chassis", lockConstants, m_leftPidController, 0)
            .addP(kp)
            .addI(ki)
            .addD(kd)
            .addFF(kff)
            .addMaxVelocity(maxVel)
            .addMaxAcceleration(maxAcc)
            .build();
        m_rightProperties = new RevPidPropertyBuilder("Chassis", lockConstants, m_rightPidController, 0)
            .addP(kp)
            .addI(ki)
            .addD(kd)
            .addFF(kff)
            .addMaxVelocity(maxVel)
            .addMaxAcceleration(maxAcc)
            .build();

        m_leftPidController.setOutputRange(kMinOutput, kMaxOutput);
        m_rightPidController.setOutputRange(kMinOutput, kMaxOutput);
        m_leftPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        m_rightPidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        m_leftPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        m_rightPidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);

        m_leftProperties.updateIfChanged(true);
        m_rightProperties.updateIfChanged(true);

        m_masterLeft.burnFlash();
        m_followerLeft.burnFlash();
        m_masterRight.burnFlash();
        m_followerRight.burnFlash();

        m_drive.setSafetyEnabled(false);

        m_fields = new ArrayList<>();
        createField("InfiniteRecharge");
        createField("Barrel");
        createField("Bounce");
        createField("GalacticSearchA");
        createField("GalacticSearchB");
        createField("Slalom");


        if (RobotBase.isSimulation()) {

            LinearSystem<N2, N2, N2> kDrivetrainPlant = LinearSystemId.identifyDrivetrainSystem(
                    DriveConstants.KV_VOLT_SECONDS_PER_METER, DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_METER,
                    DriveConstants.KV_VOLT_SECONDS_PER_RADIAN, DriveConstants.KA_VOLT_SECONDS_SQUARED_PER_RADIAN);
            DCMotor kDriveGearbox = DCMotor.getNEO(2);

            DifferentialDrivetrainSim drivetrainSim = new DifferentialDrivetrainSim(
                    kDrivetrainPlant,
                    kDriveGearbox,
                    GEAR_RATIO,
                    DriveConstants.TRACK_WIDTH_METERS,
                    Units.inchesToMeters(WHEEL_DIAMETER) / 2.0,
                    null);

            m_simulator = new DifferentialDrivetrainSimWrapper(
                    drivetrainSim,
                    new RevMotorControllerSimWrapper(m_masterLeft),
                    new RevMotorControllerSimWrapper(m_masterRight),
                    RevEncoderSimWrapper.create(m_masterLeft),
                    RevEncoderSimWrapper.create(m_masterRight),
                    new NavxWrapper().getYawGyro());
            m_simulator.setRightInverted(false);
            m_cameraSimulator = new CameraSimulator();
        }
    }

    private void createField(String name) {

        Field2d field = new Field2d();
        m_fields.add(field);
        Shuffleboard.getTab("Fields").add(name + "Field", field);
    }

    @Override
    public void periodic() {
        m_gyro.poll();
        m_odometry.update(Rotation2d.fromDegrees(getHeading()), Units.inchesToMeters(m_leftEncoder.getPosition()),
            Units.inchesToMeters(m_rightEncoder.getPosition()));

        for (Field2d field : m_fields) {
            field.setRobotPose(m_odometry.getPoseMeters());
        }

        // SmartDashboard.putNumber("x", getX());
        // SmartDashboard.putNumber("y", getY());
        // SmartDashboard.putNumber("yaw", getHeading());
        // SmartDashboard.putNumber("right encoder", getRightEncoder());
        // SmartDashboard.putNumber("left encoder", getLeftEncoder());
        SmartDashboard.putNumber("right encoder speed", getRightEncoderSpeed());
        SmartDashboard.putNumber("left encoder speed", getLeftEncoderSpeed());

        m_coordinateGuiPublisher.publish(getPose());

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
    }

    //////////////////////////////
    // Odometry Stuff
    //////////////////////////////
    public double getLeftEncoder() {
        return m_leftEncoder.getPosition();
    }

    public double getRightEncoder() {
        return m_rightEncoder.getPosition();
    }

    public double getLeftEncoderSpeed() {
        return m_leftEncoder.getVelocity();
    }

    public double getRightEncoderSpeed() {
        return m_rightEncoder.getVelocity();
    }

    public void setPositionInches(double x, double y, double angle) {
        setPositionMeters(Units.inchesToMeters(x), Units.inchesToMeters(y), angle);
    }

    public void setPositionMeters(double x, double y, double angle) {
        resetOdometry(new Pose2d(x, y, Rotation2d.fromDegrees(angle)));
    }

    public void resetOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
        m_simulator.resetOdometry(pose);
    }

    public double getXMeters() {
        return m_odometry.getPoseMeters().getTranslation().getX();
    }

    public double getYMeters() {
        return m_odometry.getPoseMeters().getTranslation().getY();
    }

    public double getXInches() {
        return Units.metersToInches(getXMeters());
    }

    public double getYInches() {
        return Units.metersToInches(getYMeters());
    }

    public double getHeading() {
        return -m_gyro.getYaw();
        // return 0;
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
    }

    public void driveDistance(double leftPosition, double rightPosition) {
        m_leftPidController.setReference(leftPosition, CANSparkMax.ControlType.kSmartMotion);
        m_rightPidController.setReference(rightPosition, CANSparkMax.ControlType.kSmartMotion);
        m_drive.feed();
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        // System.out.println("Driving velocity");
        m_leftPidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity);
        m_rightPidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity);
        m_drive.feed();

        //System.out.println("Left Velocity" + leftVelocity + ", Right Velocity" + rightVelocity);
    }

    public void smartVelocityControlMeters(double leftVelocityMeters, double rightVelocityMeters) {
        double leftVelocityInchesPerSecond = Units.metersToInches(leftVelocityMeters);
        double rightVelocityInchesPerSecond = Units.metersToInches(rightVelocityMeters);

        smartVelocityControl(leftVelocityInchesPerSecond, rightVelocityInchesPerSecond);
    }

    public void stop() {
        m_drive.stopMotor();
        System.out.println("Stopping motors");
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
        m_cameraSimulator.update(getPose());
    }
}
