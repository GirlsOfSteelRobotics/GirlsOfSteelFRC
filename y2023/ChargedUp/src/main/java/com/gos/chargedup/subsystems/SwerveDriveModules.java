package com.gos.chargedup.subsystems;


import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;


public class SwerveDriveModules {

    private final SimableCANSparkMax m_wheel;
    private final SimableCANSparkMax m_azimuth;

    public final RelativeEncoder m_wheelEncoder;

    public final RelativeEncoder m_azimuthEncoder;

    private final PidProperty m_wheelPID;

    private final String m_moduleName;

    private final PidProperty m_azimuthPID;

    private final SparkMaxPIDController m_wheelPidController;

    private final SparkMaxPIDController m_azimuthPidController;
//    public static final double kWheelDiameterMeters = 0.0762;
//    public static final int kDrivingMotorPinionTeeth = 14;
//
//    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
//
//    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
//        / kDrivingMotorReduction;
//    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
//        / kDrivingMotorReduction) / 60.0;
//
//    public static final double kTurningEncoderPositionFactor = 360;
//    public static final double kTurningEncoderVelocityFactor = 360 / 60.0;

    // TODO these are SDS mk4i ratios
    private static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);
    private static final double TURNING_GEAR_RATIO = (50.0 / 14.0) * (60.0 / 10.0);
    private static final double DRIVE_GEAR_RATIO = (50.0 / 14.0) * (19.0 / 25.0) * (45.0 / 15.0);
    private static final double DRIVE_ENCODER_CONSTANT = (1.0 / DRIVE_GEAR_RATIO) * WHEEL_DIAMETER_METERS * Math.PI;

    private SwerveModuleSimWrapper m_SimWrapper;

        public SwerveDriveModules(int wheelId, int azimuthId, String moduleName) {
        m_wheel = new SimableCANSparkMax(wheelId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_azimuth = new SimableCANSparkMax(azimuthId, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_wheelPidController = m_wheel.getPIDController();
        m_azimuthPidController = m_azimuth.getPIDController();
        m_azimuthEncoder = m_azimuth.getEncoder();
        m_azimuthEncoder.setPositionConversionFactor(360 / TURNING_GEAR_RATIO);
        m_azimuthEncoder.setVelocityConversionFactor(360 / TURNING_GEAR_RATIO / 60);
        m_wheelEncoder = m_wheel.getEncoder();
        m_wheelEncoder.setPositionConversionFactor(DRIVE_ENCODER_CONSTANT);
        m_wheelEncoder.setVelocityConversionFactor(DRIVE_ENCODER_CONSTANT / 60);

            m_wheelPID = new RevPidPropertyBuilder("Wheel PID", false, m_wheelPidController, 0)
            .addP(0)
            .addD(0)
            .addFF(0)
            .build();

        m_azimuthPID = new RevPidPropertyBuilder("Azimuth PID", false, m_azimuthPidController, 0)
            .addP(0)
            .addD(0)
            .build();

        if (RobotBase.isSimulation())
        {
            SwerveModuleSim moduleSim = new SwerveModuleSim(
                DCMotor.getNEO(1),
                DCMotor.getNEO(1),
                WHEEL_DIAMETER_METERS / 2,
                TURNING_GEAR_RATIO,
                DRIVE_GEAR_RATIO
            );
            m_SimWrapper = new SwerveModuleSimWrapper(
                moduleSim,
                new RevMotorControllerSimWrapper(m_wheel),
                new RevMotorControllerSimWrapper(m_azimuth),
                RevEncoderSimWrapper.create(m_wheel),
                RevEncoderSimWrapper.create(m_azimuth));
        }

        m_moduleName = moduleName;

    }
    public SwerveModuleSimWrapper getSimWrapper(){
            return m_SimWrapper;
    }

    public void setState(SwerveModuleState state){
        m_azimuthPID.updateIfChanged();
        m_wheelPID.updateIfChanged();
        m_wheelPidController.setReference(state.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);
        m_azimuthPidController.setReference(state.angle.getDegrees(), CANSparkMax.ControlType.kPosition);

        SmartDashboard.putNumber(m_moduleName + " Goal Angle: ", state.angle.getDegrees());
        SmartDashboard.putNumber(m_moduleName + " Current Angle: ", m_azimuth.getEncoder().getPosition());
        SmartDashboard.putNumber(m_moduleName + " Goal Velocity: ", state.speedMetersPerSecond);
        SmartDashboard.putNumber(m_moduleName + " Current Velocity: ", m_wheel.getEncoder().getVelocity());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(m_wheelEncoder.getVelocity(), Rotation2d.fromDegrees(m_azimuthEncoder.getPosition()));
    }

    public SwerveModulePosition getModulePosition(){
        return new SwerveModulePosition(m_wheelEncoder.getPosition(), Rotation2d.fromDegrees(m_azimuthEncoder.getPosition()));
    }
}

