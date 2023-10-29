package com.gos.chargedup.subsystems;


import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants;
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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;


public class SwerveDriveModules {
    private static final RevSwerveModuleConstants MODULE_CONSTANTS = new RevSwerveModuleConstants(14);

    private final SimableCANSparkMax m_drivingSparkMax;
    private final SimableCANSparkMax m_turningSparkMax;

    private final RelativeEncoder m_drivingEncoder;
    private final RelativeEncoder m_turningRelativeEncoder;
    private final AbsoluteEncoder m_turningAbsoluteEncoder;

    private final SparkMaxPIDController m_drivingPIDController;
    private final PidProperty m_drivingPIDProperty;

    private final SparkMaxPIDController m_turningPIDController;
    private final PidProperty m_turningPIDProperty;

    private final SparkMaxAlerts m_wheelAlerts;
    private final SparkMaxAlerts m_azimuthAlerts;

    private final double m_chassisAngularOffset;

    private SwerveModuleState m_currentState;
    private SwerveModulePosition m_currentPosition;
    private SwerveModuleState m_desiredState;

    private SwerveModuleSimWrapper m_simWrapper;

    private final LoggingUtil m_logger;

    /**
     * Constructs a MAXSwerveModule and configures the driving and turning motor,
     * encoder, and PID controller. This configuration is specific to the REV
     * MAXSwerve Module built with NEOs, SPARKS MAX, and a Through Bore
     * Encoder.
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public SwerveDriveModules(String moduleName, int drivingCANId, int azimuthId, double chassisAngularOffset) {
        m_drivingSparkMax = new SimableCANSparkMax(drivingCANId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_turningSparkMax = new SimableCANSparkMax(azimuthId, CANSparkMaxLowLevel.MotorType.kBrushless);

        // Factory reset, so we get the SPARKS MAX to a known state before configuring
        // them. This is useful in case a SPARK MAX is swapped out.
        m_drivingSparkMax.restoreFactoryDefaults();
        m_turningSparkMax.restoreFactoryDefaults();

        // Setup encoders and PID controllers for the driving and turning SPARKS MAX.
        m_drivingEncoder = m_drivingSparkMax.getEncoder();
        m_turningRelativeEncoder = m_turningSparkMax.getEncoder();
        m_turningAbsoluteEncoder = m_turningSparkMax.getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type.kDutyCycle);
        m_drivingPIDController = m_drivingSparkMax.getPIDController();
        m_turningPIDController = m_turningSparkMax.getPIDController();
        m_drivingPIDController.setFeedbackDevice(m_drivingEncoder);
        m_turningPIDController.setFeedbackDevice(m_turningAbsoluteEncoder);

        // Apply position and velocity conversion factors for the driving encoder. The
        // native units for position and velocity are rotations and RPM, respectively,
        // but we want meters and meters per second to use with WPILib's swerve APIs.
        m_drivingEncoder.setPositionConversionFactor(MODULE_CONSTANTS.m_drivingEncoderPositionFactor);
        m_drivingEncoder.setVelocityConversionFactor(MODULE_CONSTANTS.m_drivingEncoderVelocityFactor);

        // Apply position and velocity conversion factors for the turning encoder. We
        // want these in radians and radians per second to use with WPILib's swerve
        // APIs.
        m_turningRelativeEncoder.setPositionConversionFactor(RevSwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR);
        m_turningRelativeEncoder.setVelocityConversionFactor(RevSwerveModuleConstants.TURNING_ENCODER_VELOCITY_FACTOR);
        m_turningAbsoluteEncoder.setPositionConversionFactor(RevSwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR);
        m_turningAbsoluteEncoder.setVelocityConversionFactor(RevSwerveModuleConstants.TURNING_ENCODER_VELOCITY_FACTOR);

        // Invert the turning encoder, since the output shaft rotates in the opposite direction of
        // the steering motor in the MAXSwerve Module.
        m_turningAbsoluteEncoder.setInverted(RevSwerveModuleConstants.TURNING_ENCODER_INVERTED);

        // Enable PID wrap around for the turning motor. This will allow the PID
        // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
        // to 10 degrees will go through 0 rather than the other direction which is a
        // longer route.
        m_turningPIDController.setPositionPIDWrappingEnabled(true);
        m_turningPIDController.setPositionPIDWrappingMinInput(RevSwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MIN_INPUT);
        m_turningPIDController.setPositionPIDWrappingMaxInput(RevSwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MAX_INPUT);

        // Set the PID gains for the driving motor. Note these are example gains, and you
        // may need to tune them for your own robot!
        m_drivingPIDProperty = new RevPidPropertyBuilder("Swerve Driving PID", false, m_drivingPIDController, 0)
            .addP(0.04)
            .addD(0)
            .addFF(1 / MODULE_CONSTANTS.m_driveWheelFreeSpeedRps)
            .build();

        m_turningPIDProperty = new RevPidPropertyBuilder("Swerve Turning PID", false, m_turningPIDController, 0)
            .addP(1)
            .addD(0)
            .build();

        m_wheelAlerts = new SparkMaxAlerts(m_drivingSparkMax, "SwerveModuleDrive: " + moduleName);
        m_azimuthAlerts = new SparkMaxAlerts(m_turningSparkMax, "SwerveModuleTurning: " + moduleName);

        m_drivingSparkMax.setIdleMode(RevSwerveModuleConstants.DRIVING_MOTOR_IDLE_MODE);
        m_turningSparkMax.setIdleMode(RevSwerveModuleConstants.TURNING_MOTOR_IDLE_MODE);
        m_drivingSparkMax.setSmartCurrentLimit(RevSwerveModuleConstants.DRIVING_MOTOR_CURRENT_LIMIT);
        m_turningSparkMax.setSmartCurrentLimit(RevSwerveModuleConstants.TURNING_MOTOR_CURRENT_LIMIT);

        if (RobotBase.isSimulation()) {
            SwerveModuleSim moduleSim = new SwerveModuleSim(
                DCMotor.getNEO(1),
                DCMotor.getNEO(1),
                RevSwerveModuleConstants.WHEEL_DIAMETER_METERS / 2,
                RevSwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR,
                MODULE_CONSTANTS.m_drivingMotorReduction
            );
            m_simWrapper = new SwerveModuleSimWrapper(
                moduleSim,
                new RevMotorControllerSimWrapper(m_drivingSparkMax),
                new RevMotorControllerSimWrapper(m_turningSparkMax),
                RevEncoderSimWrapper.create(m_drivingSparkMax),
                RevEncoderSimWrapper.create(m_turningSparkMax),
                RevSwerveModuleConstants.WHEEL_DIAMETER_METERS * Math.PI,
                false);
        }

        // Save the SPARK MAX configurations. If a SPARK MAX browns out during
        // operation, it will maintain the above configurations.
        m_drivingSparkMax.burnFlash();
        m_turningSparkMax.burnFlash();

        m_chassisAngularOffset = RobotBase.isReal() ? chassisAngularOffset : 0;
        m_currentState = new SwerveModuleState();
        m_currentPosition = new SwerveModulePosition();
        m_desiredState = new SwerveModuleState();
        m_desiredState.angle = new Rotation2d(getTurningEncoderAngle());
        m_drivingEncoder.setPosition(0);

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("SwerveDrive/" + moduleName);
        m_logger = new LoggingUtil(loggingTable);
        m_logger.addDouble("Goal Angle", () -> m_desiredState.angle.getDegrees());
        m_logger.addDouble("Current Angle", () -> m_currentState.angle.getDegrees());
        m_logger.addDouble("Goal Velocity", () -> m_desiredState.speedMetersPerSecond);
        m_logger.addDouble("Current Velocity", () -> m_currentState.speedMetersPerSecond);

        m_logger.addDouble("Abs Encoder", m_turningAbsoluteEncoder::getPosition);
        m_logger.addDouble("Rel Encoder", m_turningRelativeEncoder::getPosition);
    }

    public SwerveModuleSimWrapper getSimWrapper() {
        return m_simWrapper;
    }

    private double getTurningEncoderAngle() {
        if (RobotBase.isReal()) {
            return m_turningAbsoluteEncoder.getPosition();
        }
        else {
            return m_turningRelativeEncoder.getPosition();
        }
    }

    public void periodic() {
        // Apply chassis angular offset to the encoder position to get the position
        // relative to the chassis.
        m_currentPosition = new SwerveModulePosition(
            m_drivingEncoder.getPosition(),
            new Rotation2d(getTurningEncoderAngle() - m_chassisAngularOffset));
        m_currentState = new SwerveModuleState(m_drivingEncoder.getVelocity(),
            new Rotation2d(getTurningEncoderAngle() - m_chassisAngularOffset));

        m_turningPIDProperty.updateIfChanged();
        m_drivingPIDProperty.updateIfChanged();

        m_logger.updateLogs();
        m_azimuthAlerts.checkAlerts();
        m_wheelAlerts.checkAlerts();
    }

    public SwerveModuleState getDesiredState() {
        return m_desiredState;
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState() {
        return m_currentState;
    }

    /**
     * Returns the current position of the module.
     *
     * @return The current position of the module.
     */
    public SwerveModulePosition getPosition() {
        return m_currentPosition;
    }

    /**
     * Sets the desired state for the module.
     *
     * @param desiredState Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState desiredState) {
        // Apply chassis angular offset to the desired state.
        SwerveModuleState correctedDesiredState = new SwerveModuleState();
        correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
        correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset));

        // Optimize the reference state to avoid spinning further than 90 degrees.
        SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
            new Rotation2d(getTurningEncoderAngle()));

        // Command driving and turning SPARKS MAX towards their respective setpoints.
        m_drivingPIDController.setReference(optimizedDesiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);

        if (RobotBase.isReal()) {
            m_desiredState = optimizedDesiredState;
        } else {
            m_desiredState = correctedDesiredState;
        }
        m_turningPIDController.setReference(m_desiredState.angle.getRadians(), CANSparkMax.ControlType.kPosition);

    }

    /** Zeroes all the SwerveModule encoders. */
    public void resetEncoders() {
        m_drivingEncoder.setPosition(0);
    }

    public void setSpeedPercent(double percentAzimuth, double percentWheel) {
        m_drivingSparkMax.set(percentWheel);
        m_turningSparkMax.set(percentAzimuth);
    }
}

