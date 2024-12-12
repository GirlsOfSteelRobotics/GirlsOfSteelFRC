package com.gos.lib.rev.swerve;

import com.gos.lib.rev.swerve.RevSwerveModuleConstants.DriveMotor;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants.SwerveGearingKit;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkBaseConfigAccessor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;
import org.snobotv2.module_wrappers.wpi.ADXRS450GyroWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RevSwerveChassisTest extends BasePropertiesTest {

    private static final String DRIVING_PID_PROPERTY_PREFIX = "Swerve Driving PID.mm.";
    private static final String TURNING_PID_PROPERTY_PREFIX = "Swerve Turning PID.mm.";

    private static final double TINY_EPSILON = 1e-5;

    @Test
    public void testBasicNeoSparkMaxSwerve() {

        SwerveGearingKit kit = SwerveGearingKit.HIGH;
        DriveMotor motorType = DriveMotor.NEO;

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            1, 2,
            3, 4,
            5, 6,
            7, 8,
            motorType,
            kit.m_pinionTeeth,
            kit.m_spurTeeth,
            0.381,
            0.381,
            4.5, Units.degreesToRadians(720),
            false);

        try (ADXRS450_Gyro gyro = new ADXRS450_Gyro();
             RevSwerveChassis swerveDrive = new RevSwerveChassis(swerveConstants, gyro::getRotation2d, new ADXRS450GyroWrapper(gyro))) {

            assertEquals(0.04, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kp", -1));
            assertEquals(0.0, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kd", -1));
            assertEquals(0.20817085187419282, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kff", -1));

            assertEquals(1.0, Preferences.getDouble(TURNING_PID_PROPERTY_PREFIX + "kp", -1));
            assertEquals(0.0, Preferences.getDouble(TURNING_PID_PROPERTY_PREFIX + "kd", -1));

            for (int moduleId = 0; moduleId < 4; ++moduleId) {

                // Driving Motor
                SparkBaseConfigAccessor driveConfigAccessor = swerveDrive.getDrivingMotorConfig(moduleId);

                assertEquals(IdleMode.kBrake, driveConfigAccessor.getIdleMode());
                assertEquals(50, driveConfigAccessor.getSmartCurrentLimit());

                assertEquals(0.04, driveConfigAccessor.closedLoop.getP(), TINY_EPSILON);
                assertEquals(0.0, driveConfigAccessor.closedLoop.getD(), TINY_EPSILON);
                assertEquals(0.20817085187419282, driveConfigAccessor.closedLoop.getFF(), TINY_EPSILON);
                assertEquals(FeedbackSensor.kPrimaryEncoder, driveConfigAccessor.closedLoop.getFeedbackSensor());

                assertEquals(0.0507795624434948, driveConfigAccessor.encoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(0.0507795624434948 / 60, driveConfigAccessor.encoder.getVelocityConversionFactor(), TINY_EPSILON);


                // Turning Motor
                SparkBaseConfigAccessor turnConfigAccessor = swerveDrive.getTurningMotorConfig(moduleId);

                assertEquals(IdleMode.kBrake, turnConfigAccessor.getIdleMode());
                assertEquals(20, turnConfigAccessor.getSmartCurrentLimit());

                assertEquals(1.0, turnConfigAccessor.closedLoop.getP(), TINY_EPSILON);
                assertEquals(0.0, turnConfigAccessor.closedLoop.getD(), TINY_EPSILON);
                assertEquals(FeedbackSensor.kAbsoluteEncoder, turnConfigAccessor.closedLoop.getFeedbackSensor());
                assertTrue(turnConfigAccessor.closedLoop.getPositionWrappingEnabled());
                assertEquals(0.0, turnConfigAccessor.closedLoop.getPositionWrappingMinInput(), TINY_EPSILON);
                assertEquals(2 * Math.PI, turnConfigAccessor.closedLoop.getPositionWrappingMaxInput(), TINY_EPSILON);

                assertEquals(2 * Math.PI, turnConfigAccessor.encoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI / 60, turnConfigAccessor.encoder.getVelocityConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI, turnConfigAccessor.absoluteEncoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI / 60, turnConfigAccessor.absoluteEncoder.getVelocityConversionFactor(), TINY_EPSILON);
                assertTrue(turnConfigAccessor.absoluteEncoder.getInverted());
            }
        }
    }

    @Test
    public void testBasicVortexSparkFlexSwerve() {

        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_1;
        DriveMotor motorType = DriveMotor.VORTEX;

        RevSwerveChassisConstants swerveConstants = new RevSwerveChassisConstants(
            9, 10,
            11, 12,
            13, 14,
            15, 16,
            motorType,
            kit.m_pinionTeeth,
            kit.m_spurTeeth,
            0.381,
            0.381,
            4.5, Units.degreesToRadians(720),
            false);

        try (ADXRS450_Gyro gyro = new ADXRS450_Gyro();
             RevSwerveChassis swerveDrive = new RevSwerveChassis(swerveConstants, gyro::getRotation2d, new ADXRS450GyroWrapper(gyro))) {

            assertEquals(0.04, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kp", -1));
            assertEquals(0.0, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kd", -1));
            assertEquals(0.16625437432994938, Preferences.getDouble(DRIVING_PID_PROPERTY_PREFIX + "kff", -1));

            assertEquals(1.0, Preferences.getDouble(TURNING_PID_PROPERTY_PREFIX + "kp", -1));
            assertEquals(0.0, Preferences.getDouble(TURNING_PID_PROPERTY_PREFIX + "kd", -1));

            for (int moduleId = 0; moduleId < 4; ++moduleId) {

                // Driving Motor
                SparkBaseConfigAccessor driveConfigAccessor = swerveDrive.getDrivingMotorConfig(moduleId);

                assertEquals(IdleMode.kBrake, driveConfigAccessor.getIdleMode());
                assertEquals(50, driveConfigAccessor.getSmartCurrentLimit());

                assertEquals(0.04, driveConfigAccessor.closedLoop.getP(), TINY_EPSILON);
                assertEquals(0.0, driveConfigAccessor.closedLoop.getD(), TINY_EPSILON);
                assertEquals(0.16625437140464783, driveConfigAccessor.closedLoop.getFF(), TINY_EPSILON);
                assertEquals(FeedbackSensor.kPrimaryEncoder, driveConfigAccessor.closedLoop.getFeedbackSensor());

                assertEquals(0.05319763720035553, driveConfigAccessor.encoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(0.05319763720035553 / 60, driveConfigAccessor.encoder.getVelocityConversionFactor(), TINY_EPSILON);


                // Turning Motor
                SparkBaseConfigAccessor turnConfigAccessor = swerveDrive.getTurningMotorConfig(moduleId);

                assertEquals(IdleMode.kBrake, turnConfigAccessor.getIdleMode());
                assertEquals(20, turnConfigAccessor.getSmartCurrentLimit());

                assertEquals(1.0, turnConfigAccessor.closedLoop.getP(), TINY_EPSILON);
                assertEquals(0.0, turnConfigAccessor.closedLoop.getD(), TINY_EPSILON);
                assertEquals(FeedbackSensor.kAbsoluteEncoder, turnConfigAccessor.closedLoop.getFeedbackSensor());
                assertTrue(turnConfigAccessor.closedLoop.getPositionWrappingEnabled());
                assertEquals(0.0, turnConfigAccessor.closedLoop.getPositionWrappingMinInput(), TINY_EPSILON);
                assertEquals(2 * Math.PI, turnConfigAccessor.closedLoop.getPositionWrappingMaxInput(), TINY_EPSILON);

                assertEquals(2 * Math.PI, turnConfigAccessor.encoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI / 60, turnConfigAccessor.encoder.getVelocityConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI, turnConfigAccessor.absoluteEncoder.getPositionConversionFactor(), TINY_EPSILON);
                assertEquals(2 * Math.PI / 60, turnConfigAccessor.absoluteEncoder.getVelocityConversionFactor(), TINY_EPSILON);
                assertTrue(turnConfigAccessor.absoluteEncoder.getInverted());
            }
        }
    }
}
