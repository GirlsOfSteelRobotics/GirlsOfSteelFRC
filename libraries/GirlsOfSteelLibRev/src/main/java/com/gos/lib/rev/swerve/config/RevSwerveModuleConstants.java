package com.gos.lib.rev.swerve.config;

import com.gos.lib.rev.RevMotorControllerModel;
import com.gos.lib.rev.RevMotorModel;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;

@SuppressWarnings("PMD.DataClass")
public final class RevSwerveModuleConstants {
    // Modified version of https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/Constants.java

    // Calculations required for driving motor conversion factors and feed forward
    public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(3);
    public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;

    public static final double TURNING_ENCODER_POSITION_FACTOR = (2 * Math.PI); // radians

    public final RevMotorControllerModel m_motorControllerModel;
    public final double m_drivingMotorReduction;
    public final double m_driveWheelFreeSpeedRps;

    public final SparkBaseConfig m_drivingConfig;
    public final SparkMaxConfig m_turningConfig;

    public static class DrivingClosedLoopParameters {
        public final double m_kp;
        public final double m_kd;
        public final double m_kff;

        public DrivingClosedLoopParameters(double kp, double kd, double kff) {
            m_kp = kp;
            m_kd = kd;
            m_kff = kff;
        }
    }

    public static class TurningClosedLoopParameters {
        public double m_kp;
        public double m_kd;

        public TurningClosedLoopParameters(double kp, double kd) {
            m_kp = kp;
            m_kd = kd;
        }
    }

    public RevSwerveModuleConstants(RevMotorControllerModel motorControllerModel,
                                    RevMotorModel motorModel,
                                    SwerveGearingKit kit) {
        this(motorControllerModel, motorModel, kit.m_pinionTeeth, kit.m_spurTeeth);
    }

    public RevSwerveModuleConstants(RevMotorControllerModel motorControllerModel,
                                    RevMotorModel motorModel,
                                    DriveMotorPinionTeeth pinionTeeth,
                                    DriveMotorSpurTeeth spurTeeth) {
        m_motorControllerModel = motorControllerModel;

        m_drivingMotorReduction = (45.0 * spurTeeth.m_teeth) / (pinionTeeth.m_teeth * 15);
        m_driveWheelFreeSpeedRps = (motorModel.m_freeSpeedRps * WHEEL_CIRCUMFERENCE_METERS)
            / m_drivingMotorReduction;

        double drivingEncoderPositionFactor = (WHEEL_DIAMETER_METERS * Math.PI)
            / m_drivingMotorReduction; // meters

        m_drivingConfig = m_motorControllerModel.createConfig();
        m_turningConfig = new SparkMaxConfig();

        SparkBaseConfig drivingMotorConfig = m_drivingConfig;
        SparkBaseConfig turningMotorConfig = m_turningConfig;

        drivingMotorConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(50);
        drivingMotorConfig.encoder
            .positionConversionFactor(drivingEncoderPositionFactor)
            .velocityConversionFactor(drivingEncoderPositionFactor / 60.0);
        drivingMotorConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1);

        turningMotorConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20);
        turningMotorConfig.encoder
            .positionConversionFactor(TURNING_ENCODER_POSITION_FACTOR)
            .velocityConversionFactor(TURNING_ENCODER_POSITION_FACTOR / 60);
        turningMotorConfig.absoluteEncoder
            // Invert the turning encoder, since the output shaft rotates in the opposite
            // direction of the steering motor in the MAXSwerve Module.
            .inverted(true)
            .positionConversionFactor(TURNING_ENCODER_POSITION_FACTOR)
            .velocityConversionFactor(TURNING_ENCODER_POSITION_FACTOR / 60.0);

        turningMotorConfig.closedLoop
            .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
            .outputRange(-1, 1)
            // Enable PID wrap around for the turning motor. This will allow the PID
            // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
            // to 10 degrees will go through 0 rather than the other direction which is a
            // longer route.
            .positionWrappingEnabled(true)
            .positionWrappingInputRange(0, TURNING_ENCODER_POSITION_FACTOR);

        turningMotorConfig.signals
            .absoluteEncoderPositionPeriodMs(10)
            .absoluteEncoderVelocityPeriodMs(10);
    }
}
