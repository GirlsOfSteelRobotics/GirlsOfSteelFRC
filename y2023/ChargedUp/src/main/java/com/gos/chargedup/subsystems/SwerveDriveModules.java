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

    private final SimableCANSparkMax m_drivingSparkMax;
    private final RelativeEncoder m_drivingEncoder;
    private final PidProperty m_drivingPIDProperty;
    private final SparkMaxPIDController m_drivingPIDController;

    private final SimableCANSparkMax m_turningSparkMax;
    private final RelativeEncoder m_turningRelativeEncoder;
    private final AbsoluteEncoder m_turningAbsoluteEncoder;
    private final PidProperty m_turningPIDProperty;
    private final SparkMaxPIDController m_turningPIDController;

    private final double m_chassisAngleOffset;

    private SwerveModuleState m_desiredState;

    private SwerveModuleSimWrapper m_simWrapper;

    private final LoggingUtil m_logger;

    private final SparkMaxAlerts m_wheelAlerts;

    private final SparkMaxAlerts m_azimuthAlerts;


    public SwerveDriveModules(String moduleName, int wheelId, int azimuthId, double chassisAngularOffset) {
        m_chassisAngleOffset = chassisAngularOffset;

        m_drivingSparkMax = new SimableCANSparkMax(wheelId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_drivingSparkMax.restoreFactoryDefaults();
        m_drivingSparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_drivingSparkMax.setSmartCurrentLimit(50);
        m_drivingPIDController = m_drivingSparkMax.getPIDController();

        m_drivingPIDProperty = new RevPidPropertyBuilder("Wheel PID", false, m_drivingPIDController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        m_drivingEncoder = m_drivingSparkMax.getEncoder();
        m_drivingEncoder.setPositionConversionFactor(DRIVE_ENCODER_CONSTANT);
        m_drivingEncoder.setVelocityConversionFactor(DRIVE_ENCODER_CONSTANT / 60);

        m_turningSparkMax = new SimableCANSparkMax(azimuthId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_turningSparkMax.restoreFactoryDefaults();
        m_turningSparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_turningSparkMax.setSmartCurrentLimit(20);

        m_turningPIDController = m_turningSparkMax.getPIDController();

        m_wheelAlerts = new SparkMaxAlerts(m_drivingSparkMax, "Wheel: " + moduleName);
        m_azimuthAlerts = new SparkMaxAlerts(m_turningSparkMax, "Azimuth: " + moduleName);

        m_turningRelativeEncoder = m_turningSparkMax.getEncoder();
        m_turningRelativeEncoder.setPositionConversionFactor(360 / TURNING_GEAR_RATIO);
        m_turningRelativeEncoder.setVelocityConversionFactor(360 / TURNING_GEAR_RATIO / 60);

        m_turningAbsoluteEncoder = m_turningSparkMax.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
        m_turningAbsoluteEncoder.setPositionConversionFactor(360);
        m_turningAbsoluteEncoder.setVelocityConversionFactor(360 / 60.0);
        m_turningAbsoluteEncoder.setInverted(true);

        m_turningPIDController.setFeedbackDevice(m_turningAbsoluteEncoder);
        m_turningPIDController.setPositionPIDWrappingEnabled(true);
        m_turningPIDController.setPositionPIDWrappingMinInput(0);
        m_turningPIDController.setPositionPIDWrappingMinInput(360);


        m_turningPIDProperty = new RevPidPropertyBuilder("Azimuth PID", false, m_turningPIDController, 0)
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
                new RevMotorControllerSimWrapper(m_drivingSparkMax),
                new RevMotorControllerSimWrapper(m_turningSparkMax),
                RevEncoderSimWrapper.create(m_drivingSparkMax),
                RevEncoderSimWrapper.create(m_turningSparkMax),
                WHEEL_DIAMETER_METERS * Math.PI);
        }

        m_drivingSparkMax.burnFlash();
        m_turningSparkMax.burnFlash();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("SwerveDrive/" + moduleName);

        m_desiredState = new SwerveModuleState();

        m_logger = new LoggingUtil(loggingTable);
        m_logger.addDouble("Goal Angle", () -> m_desiredState.angle.getDegrees());
        m_logger.addDouble("Current Angle", this::getTurningEncoderAngle);
        m_logger.addDouble("Goal Velocity", () -> m_desiredState.speedMetersPerSecond);
        m_logger.addDouble("Current Velocity", m_drivingEncoder::getVelocity);

        m_logger.addDouble("Abs Encoder", m_turningAbsoluteEncoder::getPosition);
        m_logger.addDouble("Rel Encoder", m_turningRelativeEncoder::getPosition);

    }

    public SwerveModuleSimWrapper getSimWrapper() {
        return m_simWrapper;
    }

    private double getTurningEncoderAngle() {
        if (RobotBase.isReal()) {
            return m_turningAbsoluteEncoder.getPosition() - m_chassisAngleOffset;
        }
        else {
            return m_turningRelativeEncoder.getPosition();
        }
    }

    public void periodic() {
        m_logger.updateLogs();
        m_azimuthAlerts.checkAlerts();
        m_wheelAlerts.checkAlerts();
    }

    public SwerveModuleState getDesiredState() {
        return m_desiredState;
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_drivingEncoder.getVelocity(), Rotation2d.fromDegrees(getTurningEncoderAngle()));
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(m_drivingEncoder.getPosition(), Rotation2d.fromDegrees(getTurningEncoderAngle()));
    }

    public void setDesiredState(SwerveModuleState rawState) {
        SwerveModuleState optimizedState = SwerveModuleState.optimize(rawState, Rotation2d.fromDegrees(getTurningEncoderAngle()));
        Rotation2d offsetAngle = optimizedState.angle;
        if (RobotBase.isReal()) {
            offsetAngle = offsetAngle.plus(Rotation2d.fromDegrees(m_chassisAngleOffset));
        }
        m_desiredState = new SwerveModuleState(optimizedState.speedMetersPerSecond, offsetAngle);
        m_turningPIDProperty.updateIfChanged();
        m_drivingPIDProperty.updateIfChanged();
        m_drivingPIDController.setReference(m_desiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
        m_turningPIDController.setReference(m_desiredState.angle.getDegrees(), CANSparkMax.ControlType.kPosition);
    }

    public void setSpeedPercent(double percentAzimuth, double percentWheel) {
        m_drivingSparkMax.set(percentWheel);
        m_turningSparkMax.set(percentAzimuth);
    }


}
