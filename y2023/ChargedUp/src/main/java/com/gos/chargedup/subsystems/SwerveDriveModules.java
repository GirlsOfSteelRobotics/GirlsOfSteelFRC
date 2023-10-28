package com.gos.chargedup.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;


public class SwerveDriveModules {

    // TODO these are SDS mk4i ratios
    private static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);

    private static final int DRIVING_MOTOR_PINION_TEETH = 14;
    private static final double DRIVING_MOTOR_REDUCTION = (45.0 * 22) / (DRIVING_MOTOR_PINION_TEETH * 15);
    private static final double DRIVE_ENCODER_CONSTANT = (WHEEL_DIAMETER_METERS * Math.PI) / DRIVING_MOTOR_REDUCTION;

    private static final double TURNING_GEAR_RATIO = 9424.0 / 203;

    private final SimableCANSparkMax m_wheel;
    private final RelativeEncoder m_wheelEncoder;
    private final PidProperty m_wheelPID;
    private final SparkMaxPIDController m_wheelPidController;

    private final SimableCANSparkMax m_azimuth;
    private final RelativeEncoder m_azimuthRelativeEncoder;
    private final AbsoluteEncoder m_azimuthAbsoluteEncoder;
    private final PidProperty m_azimuthPID;
    private final SparkMaxPIDController m_azimuthPidController;

    private final double m_chassisAngleOffset;

    private SwerveModuleState m_desiredState;

    private SwerveModuleSimWrapper m_simWrapper;

    private final LoggingUtil m_logger;

    private final SparkMaxAlerts m_wheelAlerts;

    private final SparkMaxAlerts m_azimuthAlerts;


    public SwerveDriveModules(String moduleName, int wheelId, int azimuthId, double chassisAngularOffset) {
        m_chassisAngleOffset = chassisAngularOffset;

        m_wheel = new SimableCANSparkMax(wheelId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_wheel.restoreFactoryDefaults();
        m_wheel.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_wheel.setSmartCurrentLimit(50);
        m_wheelPidController = m_wheel.getPIDController();

        m_wheelPID = new RevPidPropertyBuilder("Wheel PID", false, m_wheelPidController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        m_wheelEncoder = m_wheel.getEncoder();
        m_wheelEncoder.setPositionConversionFactor(DRIVE_ENCODER_CONSTANT);
        m_wheelEncoder.setVelocityConversionFactor(DRIVE_ENCODER_CONSTANT / 60);

        m_azimuth = new SimableCANSparkMax(azimuthId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_azimuth.restoreFactoryDefaults();
        m_azimuth.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_azimuth.setSmartCurrentLimit(20);

        m_azimuthPidController = m_azimuth.getPIDController();

        m_wheelAlerts = new SparkMaxAlerts(m_wheel, "Wheel: " + moduleName);
        m_azimuthAlerts = new SparkMaxAlerts(m_azimuth, "Azimuth: " + moduleName);

        m_azimuthRelativeEncoder = m_azimuth.getEncoder();
        m_azimuthRelativeEncoder.setPositionConversionFactor(360 / TURNING_GEAR_RATIO);
        m_azimuthRelativeEncoder.setVelocityConversionFactor(360 / TURNING_GEAR_RATIO / 60);

        m_azimuthAbsoluteEncoder = m_azimuth.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
        m_azimuthAbsoluteEncoder.setPositionConversionFactor(360);
        m_azimuthAbsoluteEncoder.setVelocityConversionFactor(360 / 60.0);
        m_azimuthAbsoluteEncoder.setInverted(true);

        m_azimuthPidController.setFeedbackDevice(m_azimuthAbsoluteEncoder);
        m_azimuthPidController.setPositionPIDWrappingEnabled(true);
        m_azimuthPidController.setPositionPIDWrappingMinInput(0);
        m_azimuthPidController.setPositionPIDWrappingMinInput(360);


        m_azimuthPID = new RevPidPropertyBuilder("Azimuth PID", false, m_azimuthPidController, 0)
            .addP(0)
            .addD(0)
            .build();

        if (RobotBase.isSimulation()) {
            SwerveModuleSim moduleSim = new SwerveModuleSim(
                DCMotor.getNEO(1),
                DCMotor.getNEO(1),
                WHEEL_DIAMETER_METERS / 2,
                TURNING_GEAR_RATIO,
                DRIVING_MOTOR_REDUCTION
            );
            m_simWrapper = new SwerveModuleSimWrapper(
                moduleSim,
                new RevMotorControllerSimWrapper(m_wheel),
                new RevMotorControllerSimWrapper(m_azimuth),
                RevEncoderSimWrapper.create(m_wheel),
                RevEncoderSimWrapper.create(m_azimuth),
                WHEEL_DIAMETER_METERS * Math.PI);
        }

        m_wheel.burnFlash();
        m_azimuth.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("SwerveDrive/" + moduleName);

        m_desiredState = new SwerveModuleState();

        m_logger = new LoggingUtil(loggingTable);
        m_logger.addDouble("Goal Angle", () -> m_desiredState.angle.getDegrees());
        m_logger.addDouble("Current Angle", this::getAzimuthAngle);
        m_logger.addDouble("Goal Velocity", () -> m_desiredState.speedMetersPerSecond);
        m_logger.addDouble("Current Velocity", m_wheelEncoder::getVelocity);

        m_logger.addDouble("Abs Encoder", m_azimuthAbsoluteEncoder::getPosition);
        m_logger.addDouble("Rel Encoder", m_azimuthRelativeEncoder::getPosition);

    }

    private double getAzimuthAngle() {
        if (RobotBase.isReal()) {
            return m_azimuthAbsoluteEncoder.getPosition() - m_chassisAngleOffset;
        }
        else {
            return m_azimuthRelativeEncoder.getPosition();
        }
    }

    public SwerveModuleSimWrapper getSimWrapper() {
        return m_simWrapper;
    }

    public void update() {
        m_logger.updateLogs();
        m_azimuthAlerts.checkAlerts();
        m_wheelAlerts.checkAlerts();
    }

    public void setState(SwerveModuleState rawState) {
        SwerveModuleState optimizedState = SwerveModuleState.optimize(rawState, Rotation2d.fromDegrees(getAzimuthAngle()));
        Rotation2d offsetAngle = optimizedState.angle;
        if (RobotBase.isReal()) {
            offsetAngle = offsetAngle.plus(Rotation2d.fromDegrees(m_chassisAngleOffset));
        }
        m_desiredState = new SwerveModuleState(optimizedState.speedMetersPerSecond, offsetAngle);
        m_azimuthPID.updateIfChanged();
        m_wheelPID.updateIfChanged();
        m_wheelPidController.setReference(m_desiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
        m_azimuthPidController.setReference(m_desiredState.angle.getDegrees(), CANSparkMax.ControlType.kPosition);
    }

    public SwerveModuleState getDesiredState() {
        return m_desiredState;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_wheelEncoder.getVelocity(), Rotation2d.fromDegrees(getAzimuthAngle()));
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(m_wheelEncoder.getPosition(), Rotation2d.fromDegrees(getAzimuthAngle()));
    }

    public void setSpeedPercent(double percentAzimuth, double percentWheel) {
        m_wheel.set(percentWheel);
        m_azimuth.set(percentAzimuth);
    }


}
