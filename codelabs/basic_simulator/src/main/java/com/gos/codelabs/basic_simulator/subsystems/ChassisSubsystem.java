package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.Constants;
import org.wpilib.hardware.bus.CANPort;
import com.gos.codelabs.basic_simulator.commands.AutoDriveStraightDistanceCommand;
import com.gos.codelabs.basic_simulator.commands.AutoDriveStraightTimedCommand;
import com.gos.codelabs.basic_simulator.commands.TurnToAngleCommand;
import com.gos.lib.logging.LoggingUtil;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import org.wpilib.math.linalg.VecBuilder;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.DifferentialDriveKinematics;
import org.wpilib.math.kinematics.DifferentialDriveOdometry;
import org.wpilib.math.numbers.N2;
import org.wpilib.math.system.LinearSystem;
import org.wpilib.math.system.DCMotor;
import org.wpilib.math.system.Models;
import org.wpilib.math.util.Units;
import org.wpilib.hardware.imu.OnboardIMU;
import org.wpilib.framework.RobotBase;
import org.wpilib.drive.DifferentialDrive;
import com.gos.lib.tunables.CommandTab;
import org.wpilib.simulation.DifferentialDrivetrainSim;
import org.wpilib.smartdashboard.Field2d;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.command2.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.module_wrappers.wpi.OnboardIMUWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

public class ChassisSubsystem extends SubsystemBase implements AutoCloseable {

    private final SparkMax m_leftDriveA;
    private final SparkMax m_leftDriveB;
    private final SparkMax m_rightDriveA;
    private final SparkMax m_rightDriveB;

    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;

    private final DifferentialDrive m_differentialDrive;
    private final DifferentialDriveOdometry m_odometry;
    private final Field2d m_field;

    private final OnboardIMU m_gyro;

    private final LoggingUtil m_loggingUtil;

    private DifferentialDrivetrainSimWrapper m_simulator;

    public static final class DrivetrainConstants {

        public static final DCMotor DRIVE_GEARBOX = DCMotor.getCIM(2);
        public static final double K_DRIVE_GEARING = 8;

        public static final double K_TRACK_WIDTH_METERS = 0.762;
        public static final double K_WHEEL_DIAMETER_METERS = 0.1016;

        public static final double KS_VOLTS = 0.22;
        public static final double KV_VOLT_SECONDS_PER_METER = 1.98;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.2;
        public static final double KV_VOLT_SECONDS_PER_RADIAN = 1.5;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_RADIAN = 0.3;

        public static final LinearSystem<N2, N2, N2> K_DRIVETRAIN_PLANT =
                Models.differentialDriveFromSysId(KV_VOLT_SECONDS_PER_METER, KA_VOLT_SECONDS_SQUARED_PER_METER,
                        KV_VOLT_SECONDS_PER_RADIAN, KA_VOLT_SECONDS_SQUARED_PER_RADIAN);

        public static final DifferentialDriveKinematics DRIVE_KINEMATICS =
                new DifferentialDriveKinematics(K_TRACK_WIDTH_METERS);

        public static DifferentialDrivetrainSim createSim() {
            return new DifferentialDrivetrainSim(
                    K_DRIVETRAIN_PLANT,
                    DRIVE_GEARBOX,
                    K_DRIVE_GEARING,
                    K_TRACK_WIDTH_METERS,
                    K_WHEEL_DIAMETER_METERS / 2.0,
                    Constants.SIMULATE_SENSOR_NOISE ? VecBuilder.fill(0, 0, 0.0001, 0.1, 0.1, 0.005, 0.005) : null); // NOPMD
        }

        private DrivetrainConstants() {

        }
    }

    public ChassisSubsystem() {

        m_leftDriveA = new SparkMax(CANPort.CAN_S0, Constants.CAN_CHASSIS_LEFT_A, MotorType.kBrushless);
        m_leftDriveB = new SparkMax(CANPort.CAN_S0, Constants.CAN_CHASSIS_LEFT_B, MotorType.kBrushless);
        SparkMaxConfig leftDriveAConfig = new SparkMaxConfig();
        SparkMaxConfig leftDriveBConfig = new SparkMaxConfig();
        leftDriveBConfig.follow(m_leftDriveA);

        m_rightDriveA = new SparkMax(CANPort.CAN_S0, Constants.CAN_CHASSIS_RIGHT_A, MotorType.kBrushless);
        m_rightDriveB = new SparkMax(CANPort.CAN_S0, Constants.CAN_CHASSIS_RIGHT_B, MotorType.kBrushless);
        SparkMaxConfig rightDriveAConfig = new SparkMaxConfig();
        SparkMaxConfig rightDriveBConfig = new SparkMaxConfig();
        rightDriveBConfig.follow(m_rightDriveA);
        rightDriveAConfig.inverted(true);

        m_leftEncoder = m_leftDriveA.getEncoder();
        m_rightEncoder = m_rightDriveA.getEncoder();

        m_gyro = new OnboardIMU(OnboardIMU.MountOrientation.FLAT);

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);
        m_field = new Field2d();

        m_loggingUtil = new LoggingUtil("Chassis");
        m_loggingUtil.addDouble("Left Distance", this::getLeftDistance);
        m_loggingUtil.addDouble("Right Distance", this::getRightDistance);
        m_loggingUtil.addDouble("Average Distance", this::getAverageDistance);
        m_loggingUtil.addDouble("Heading", this::getHeading);

        if (RobotBase.isSimulation()) {
            m_simulator = new DifferentialDrivetrainSimWrapper(
                    DrivetrainConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_leftDriveA, DrivetrainConstants.DRIVE_GEARBOX),
                    new RevMotorControllerSimWrapper(m_rightDriveA, DrivetrainConstants.DRIVE_GEARBOX),
                    RevEncoderSimWrapper.create(m_leftDriveA),
                    RevEncoderSimWrapper.create(m_rightDriveA),
                    new OnboardIMUWrapper(m_gyro));
            m_simulator.setRightInverted(false);

            m_differentialDrive.setSafetyEnabled(false);
        }

        m_leftDriveA.configure(leftDriveAConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_leftDriveB.configure(leftDriveBConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rightDriveA.configure(rightDriveAConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_rightDriveB.configure(rightDriveBConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void close() {
        m_leftDriveA.close();
        m_leftDriveB.close();
        m_rightDriveA.close();
        m_rightDriveB.close();
        
    }

    /**
     * Drives the robot.
     *
     * @param throttle How fast to drive forwards [-1, 1]. Positive is forwards
     * @param steer How fast to turn [-1, 1]. Positive is counter-clockwise (turning left), like the rest of WPILib
     */
    public void arcadeDrive(double throttle, double steer) {
        // TODO implement
    }

    public void stop() {
        // TODO implement
    }

    @Override
    public void periodic() {
        Telemetry.log("Field", m_field);
        m_odometry.update(m_gyro.getRotation2d(), getLeftDistance(), getRightDistance());
        m_field.setRobotPose(m_odometry.getPose());
        m_loggingUtil.updateLogs();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    /**
     * Gets the direction the robot is facing.
     *
     * @return The heading, in degrees. Positive is counter-clockwise (turning left)
     */
    public double getHeading() {
        // TODO implement
        return 0;
    }

    /**
     * @return How far the left side of the robot has driven, in meters
     */
    public double getLeftDistance() {
        // TODO implement
        return 0;
    }

    /**
     * @return How far the right side of the robot has driven, in meters
     */
    public double getRightDistance() {
        // TODO implement
        return 0;
    }

    public double getAverageDistance() {
        // TODO implement
        return 0;
    }

    public void addChassisDebugCommands() {
        CommandTab tab = new CommandTab("Chassis");
        tab.add(new AutoDriveStraightTimedCommand(this, .5, 2).withName("Timed Drive Forwards"));
        tab.add(new AutoDriveStraightTimedCommand(this, -.5, 2).withName("Timed Drive Backwards"));
        tab.add(new AutoDriveStraightDistanceCommand(this, Units.feetToMeters(5)).withName("Drive Distance Forwards"));
        tab.add(new AutoDriveStraightDistanceCommand(this, Units.feetToMeters(-5)).withName("Drive Distance Backwards"));
        tab.add(new TurnToAngleCommand(this, 90).withName("Turn To 90"));
        tab.add(new TurnToAngleCommand(this, -45).withName("Turn To -45"));
        tab.add(new TurnToAngleCommand(this, 0).withName("Turn To 0"));
    }
}
