package com.gos.chargedup.subsystems;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.gos.chargedup.Constants;
import com.gos.chargedup.commands.RobotMotorsMove;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

import java.util.Optional;


public class ChassisSubsystem extends SubsystemBase {
    private static final GosDoubleProperty AUTO_ENGAGE_SPEED = new GosDoubleProperty(false, "Chassis speed for auto engage", 0.1);

    private static final double PITCH_LOWER_LIMIT = -5.0;
    private static final double PITCH_UPPER_LIMIT = 5.0;

    private static final double WHEEL_DIAMETER = Units.inchesToMeters(6.0);
    private static final double GEAR_RATIO = 40.0 / 12.0 * 40.0 / 14.0;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private static final double TRACK_WIDTH = 0.381 * 2; //set this to the actual
    public static final DifferentialDriveKinematics K_DRIVE_KINEMATICS =
        new DifferentialDriveKinematics(TRACK_WIDTH);

    //Chassis and motors
    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;
    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final DifferentialDrive m_drive;

    //Odometry
    private final DifferentialDriveOdometry m_odometry;
    private final WPI_PigeonIMU m_gyro;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;

    //Field
    private final Field2d m_field;

    private final VisionSubsystem m_pcw;

    private final DifferentialDrivePoseEstimator m_poseEstimator;


    private final SparkMaxPIDController m_leftPIDcontroller;
    private final SparkMaxPIDController m_rightPIDcontroller;

    private final PidProperty m_leftPIDProperties;
    private final PidProperty m_rightPIDProperties;

    private final NetworkTableEntry m_gyroAngleDegEntry;

    //SIM
    private DifferentialDrivetrainSimWrapper m_simulator;


    public ChassisSubsystem() {

        m_leaderLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leaderLeft.restoreFactoryDefaults();
        m_followerLeft.restoreFactoryDefaults();
        m_leaderRight.restoreFactoryDefaults();
        m_followerRight.restoreFactoryDefaults();

        m_leaderLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_leaderRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

        m_leaderLeft.setInverted(false);
        m_leaderRight.setInverted(true);

        m_followerLeft.follow(m_leaderLeft, false);
        m_followerRight.follow(m_leaderRight, false);

        m_drive = new DifferentialDrive(m_leaderLeft, m_leaderRight);

        m_gyro = new WPI_PigeonIMU(Constants.PIGEON_PORT);
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), 0, 0);

        m_leftPIDcontroller = m_leaderLeft.getPIDController();
        m_rightPIDcontroller = m_leaderRight.getPIDController();

        m_leftPIDProperties = setupPidValues(m_leftPIDcontroller);
        m_rightPIDProperties = setupPidValues(m_rightPIDcontroller);

        m_rightEncoder = m_leaderRight.getEncoder();
        m_leftEncoder = m_leaderLeft.getEncoder();

        m_leftEncoder.setPositionConversionFactor(ENCODER_CONSTANT);
        m_rightEncoder.setPositionConversionFactor(ENCODER_CONSTANT);

        m_leftEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);
        m_rightEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);

        m_leaderLeft.burnFlash();
        m_followerLeft.burnFlash();
        m_leaderRight.burnFlash();
        m_followerRight.burnFlash();

        m_field = new Field2d();
        SmartDashboard.putData(m_field);

        m_poseEstimator = new DifferentialDrivePoseEstimator(
            K_DRIVE_KINEMATICS, m_gyro.getRotation2d(), 0.0, 0.0, new Pose2d());

        m_pcw = new VisionSubsystem();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("ChassisSubsystem");
        m_gyroAngleDegEntry = loggingTable.getEntry("Gyro Angle (deg)");

        if (RobotBase.isSimulation()) {
            DifferentialDrivetrainSim drivetrainSim = DifferentialDrivetrainSim.createKitbotSim(
                DifferentialDrivetrainSim.KitbotMotor.kDoubleNEOPerSide,
                DifferentialDrivetrainSim.KitbotGearing.k5p95,
                DifferentialDrivetrainSim.KitbotWheelSize.kSixInch,
                null);
            m_simulator = new DifferentialDrivetrainSimWrapper(
                drivetrainSim,
                new RevMotorControllerSimWrapper(m_leaderLeft),
                new RevMotorControllerSimWrapper(m_leaderRight),
                RevEncoderSimWrapper.create(m_leaderLeft),
                RevEncoderSimWrapper.create(m_leaderRight),
                new CtrePigeonImuWrapper(m_gyro));
            m_simulator.setRightInverted(false);
        }

    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Chassis", false, pidController, 0)
            .addP(0) //this needs to be tuned!
            .addI(0)
            .addD(0)
            .addFF(0)
            .addMaxVelocity((Units.inchesToMeters(0)))
            .addMaxAcceleration((Units.inchesToMeters(0)))
            .build();
    }

    @Override
    public void periodic() {
        updateOdometry();

        m_field.getObject("oldOdom").setPose(m_odometry.getPoseMeters());
        m_field.setRobotPose(m_poseEstimator.getEstimatedPosition());

        m_leftPIDProperties.updateIfChanged();
        m_rightPIDProperties.updateIfChanged();

        m_gyroAngleDegEntry.setNumber(getYaw());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        m_leftPIDcontroller.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0);
        m_rightPIDcontroller.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0);
    }

    private void stop() {
        setArcadeDrive(0, 0);
    }

    public void trapezoidMotionControl(double leftDistance, double rightDistance) {
        m_leftPIDcontroller.setReference(leftDistance, CANSparkMax.ControlType.kSmartMotion, 0);
        m_rightPIDcontroller.setReference(rightDistance, CANSparkMax.ControlType.kSmartMotion, 0);

    }

    public void setArcadeDrive(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }

    public void setCurvatureDrive(double speed, double steer, boolean allowTurnInPlace) {
        m_drive.curvatureDrive(speed, steer, allowTurnInPlace);
    }

    public double getPitch() {
        return m_gyro.getPitch();
    }

    public double getYaw() {
        return m_gyro.getYaw();
    }

    public void autoEngage() {
        if (getPitch() > PITCH_LOWER_LIMIT && getPitch() < PITCH_UPPER_LIMIT) {
            setArcadeDrive(0, 0);

        } else if (getPitch() > 0) {
            setArcadeDrive(-AUTO_ENGAGE_SPEED.getValue(), 0);

        } else if (getPitch() < 0) {
            setArcadeDrive(AUTO_ENGAGE_SPEED.getValue(), 0);
        }
    }

    //NEW ODOMETRY
    public void updateOdometry() {
        m_odometry.update(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_poseEstimator.update(
            m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

        Optional<EstimatedRobotPose> result =
            m_pcw.getEstimatedGlobalPose(m_poseEstimator.getEstimatedPosition());
        if (result.isPresent()) {
            EstimatedRobotPose camPose = result.get();
            Pose2d pose2d = camPose.estimatedPose.toPose2d();
            m_poseEstimator.addVisionMeasurement(pose2d, camPose.timestampSeconds);
            m_field.getObject("Camera Estimated Position").setPose(pose2d);
        } else {
            // move it way off the screen to make it disappear
            m_field.getObject("Camera Estimated Position").setPose(new Pose2d(-100, -100, new Rotation2d()));
        }
    }

    public void resetOdometry(Pose2d pose2d) {
        m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        m_poseEstimator.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        System.out.println("Reset Odometry was called");
    }

    ////////////////////
    // Command Factories
    ////////////////////
    public CommandBase commandChassisVelocity() {
        return this.runEnd(
            () -> smartVelocityControl(Units.feetToMeters(5), Units.feetToMeters(5)),
            this::stop);
    }

    public CommandBase createAutoEngageCommand() {
        return run(this::autoEngage).withName("AutoEngage");
    }

    public Command followTrajectoryCommand(PathPlannerTrajectory traj, boolean isFirstPath) {
        return new SequentialCommandGroup(
            new InstantCommand(() -> {
                // Reset odometry for the first path you run during auto
                if (isFirstPath) {
                    this.resetOdometry(traj.getInitialPose());
                }
            }).repeatedly().withTimeout(1),
            new PPRamseteCommand(
                traj,
                this::getPose, // Pose supplier
                new RamseteController(),
                K_DRIVE_KINEMATICS, // DifferentialDriveKinematics
                this::smartVelocityControl, // DifferentialDriveWheelSpeeds supplier
                true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
                this // Requires this drive subsystem
            ));
    }

    public CommandBase createIsLeftMotorMoving() {
        return new RobotMotorsMove(m_leaderLeft, "Chassis: Leader left motor", 1.0);
    }

    public CommandBase createIsRightMotorMoving() {
        return new RobotMotorsMove(m_leaderRight, "Chassis: Leader right motor", 1.0);
    }
}
