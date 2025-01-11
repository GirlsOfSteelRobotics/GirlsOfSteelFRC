package com.gos.reefscape.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.gos.lib.rev.swerve.config.RevSwerveModuleConstants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.swerve.SwerveModuleSim;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SwerveModuleSimWrapper;

public class RevSwerveModule {
    private static final double GEAR_REDUCTION = 5.9;


    private final CANcoder m_absoluteEncoder;
    private final RelativeEncoder m_driveEncoder;
    private final RelativeEncoder m_steerEncoder;
    private final SparkFlex m_motorDrive;
    private final SparkFlex m_motorSteer;
    private final SparkClosedLoopController m_drivePID;
    private final SparkClosedLoopController m_steerPID;

    private SwerveModuleSimWrapper m_simWrapper;



    public RevSwerveModule(int absoluteEncoderID, int motorDriveID, int motorSteerID) {
        m_absoluteEncoder = new CANcoder(absoluteEncoderID);
        m_motorDrive = new SparkFlex(motorDriveID, MotorType.kBrushless);
        m_motorSteer = new SparkFlex(motorSteerID, MotorType.kBrushless);
        m_driveEncoder = m_motorDrive.getEncoder();
        m_steerEncoder = m_motorSteer.getEncoder();

        m_drivePID = m_motorDrive.getClosedLoopController();
        m_steerPID = m_motorSteer.getClosedLoopController();


        if (RobotBase.isSimulation()) {
            DCMotor turningMotor = DCMotor.getNEO(1);
            DCMotor drivingMotor = DCMotor.getNEO(1);
            SwerveModuleSim moduleSim = new SwerveModuleSim(
                turningMotor,
                drivingMotor,
                RevSwerveModuleConstants.WHEEL_DIAMETER_METERS / 2,
                RevSwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR,
                GEAR_REDUCTION,
                1.0,
                1.8, // Seems fishy
                1.1,
                0.8,
                16.0,
                0.001
            );
            m_simWrapper = new SwerveModuleSimWrapper(
                moduleSim,
                new RevMotorControllerSimWrapper(m_motorDrive, drivingMotor),
                new RevMotorControllerSimWrapper(m_motorSteer, turningMotor),
                RevEncoderSimWrapper.create(m_motorDrive),
                RevEncoderSimWrapper.create(m_motorSteer),
                RevSwerveModuleConstants.WHEEL_DIAMETER_METERS * Math.PI,
                false);
        }
    }

    public void drive(double velocity, double angle) {
        m_drivePID.setReference(velocity, ControlType.kVelocity);
        m_steerPID.setReference(angle, ControlType.kPosition);
    }

    public double getAbsoluteEncoderPosition() {
        return m_absoluteEncoder.getAbsolutePosition().getValueAsDouble();
    }

    public double getVelocity() {
        return m_driveEncoder.getVelocity();

    }

    public double getSteerAngle() {
        return m_steerEncoder.getPosition();
    }

    public SwerveModuleSimWrapper getSimWrapper() {
        return m_simWrapper;
    }
}
