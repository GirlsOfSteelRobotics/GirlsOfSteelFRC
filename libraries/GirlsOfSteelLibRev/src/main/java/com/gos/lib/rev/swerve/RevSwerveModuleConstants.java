package com.gos.lib.rev.swerve;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.util.Units;

@SuppressWarnings("PMD.DataClass")
public final class RevSwerveModuleConstants {
    // Modified version of https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/Constants.java

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean TURNING_ENCODER_INVERTED = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double DRIVING_MOTOR_FREE_SPEED_RPS = NeoMotorConstants.FREE_SPEED_RPM / 60;
    public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(3);
    public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;

    public static final double TURNING_ENCODER_POSITION_FACTOR = (2 * Math.PI); // radians
    public static final double TURNING_ENCODER_VELOCITY_FACTOR = (2 * Math.PI) / 60.0; // radians per second

    public static final double TURNING_ENCODER_POSITION_PID_MIN_INPUT = 0; // radians
    public static final double TURNING_ENCODER_POSITION_PID_MAX_INPUT = TURNING_ENCODER_POSITION_FACTOR; // radians

    public static final CANSparkMax.IdleMode DRIVING_MOTOR_IDLE_MODE = CANSparkMax.IdleMode.kBrake;
    public static final CANSparkMax.IdleMode TURNING_MOTOR_IDLE_MODE = CANSparkMax.IdleMode.kBrake;

    public static final int DRIVING_MOTOR_CURRENT_LIMIT = 50; // amps
    public static final int TURNING_MOTOR_CURRENT_LIMIT = 20; // amps

    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public final double m_drivingMotorReduction;
    public final double m_driveWheelFreeSpeedRps;

    public final double m_drivingEncoderPositionFactor;
    public final double m_drivingEncoderVelocityFactor;

    public static final class NeoMotorConstants {
        public static final double FREE_SPEED_RPM = 5676;
    }

    public enum DriveMotorTeeth {
        T12(12, Units.feetToMeters(13.51)),
        T13(13, Units.feetToMeters(14.63)),
        T14(14, Units.feetToMeters(15.76));

        public final int m_teeth;
        public final double m_theoreticalFreeSpeedMps;

        DriveMotorTeeth(int teeth, double theoreticalFreeSpeedMps) {
            m_teeth = teeth;
            m_theoreticalFreeSpeedMps = theoreticalFreeSpeedMps;
        }
    }

    public RevSwerveModuleConstants(DriveMotorTeeth teeth) {
        m_drivingMotorReduction = (45.0 * 22) / (teeth.m_teeth * 15);
        m_driveWheelFreeSpeedRps = (DRIVING_MOTOR_FREE_SPEED_RPS * WHEEL_CIRCUMFERENCE_METERS)
            / m_drivingMotorReduction;

        m_drivingEncoderPositionFactor = (WHEEL_DIAMETER_METERS * Math.PI)
            / m_drivingMotorReduction; // meters
        m_drivingEncoderVelocityFactor = ((WHEEL_DIAMETER_METERS * Math.PI)
            / m_drivingMotorReduction) / 60.0; // meters per second
    }
}
