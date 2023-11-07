package com.gos.chargedup.subsystems;

import com.gos.chargedup.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.pid.PidProperty;
import com.gos.lib.rev.properties.pid.RevPidPropertyBuilder;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.BaseAutoBuilder;
import com.pathplanner.lib.auto.RamseteAutoBuilder;
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
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@SuppressWarnings("PMD.GodClass")
public class TankDriveChassisSubsystem extends BaseChassis implements ChassisSubsystemInterface {
    private static final GosDoubleProperty AUTO_ENGAGE_KP = new GosDoubleProperty(false, "Chassis auto engage kP", .025);

    private static final double WHEEL_DIAMETER = Units.inchesToMeters(6.0);
    private static final double GEAR_RATIO;

    static {
        if (Constants.IS_ROBOT_BLOSSOM) {
            GEAR_RATIO = 527.0 / 54.0;
        } else {
            GEAR_RATIO = 40.0 / 12.0 * 40.0 / 14.0;
        }
    }

    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private static final double TRACK_WIDTH = 0.381 * 2; //set this to the actual
    public static final DifferentialDriveKinematics K_DRIVE_KINEMATICS =
        new DifferentialDriveKinematics(TRACK_WIDTH);
    public static final double KS_VOLTS_STATIC_FRICTION_TURNING = 0;


    //Chassis and motors
    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;
    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final DifferentialDrive m_drive;

    //Odometry
    private final DifferentialDriveOdometry m_odometry;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;

    //SIM
    private DifferentialDrivetrainSimWrapper m_simulator;

    private final DifferentialDrivePoseEstimator m_poseEstimator;

    private final SparkMaxPIDController m_leftPIDcontroller;
    private final SparkMaxPIDController m_rightPIDcontroller;

    private final PidProperty m_leftPIDProperties;
    private final PidProperty m_rightPIDProperties;


    private final NetworkTableEntry m_trajectoryLeftWheelSpeedGoal;
    private final NetworkTableEntry m_trajectoryRightWheelSpeedGoal;

    private final GosDoubleProperty m_maxVelocity = new GosDoubleProperty(true, "Chassis Trajectory Max Velocity", 60);

    private final SparkMaxAlerts m_leaderLeftMotorErrorAlert;
    private final SparkMaxAlerts m_followerLeftMotorErrorAlert;
    private final SparkMaxAlerts m_leaderRightMotorErrorAlert;
    private final SparkMaxAlerts m_followerRightMotorErrorAlert;

    //max velocity and acceleration tuning
    private final GosDoubleProperty m_onTheFlyMaxVelocity = new GosDoubleProperty(true, "Chassis On the Fly Max Acceleration", 48);
    private final GosDoubleProperty m_onTheFlyMaxAcceleration = new GosDoubleProperty(true, "Chassis On the Fly Max Acceleration", 48);

    @SuppressWarnings({"PMD.NcssCount", "PMD.ExcessiveMethodLength"})
    public TankDriveChassisSubsystem() {

        m_leaderLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_leaderLeft.restoreFactoryDefaults();
        m_followerLeft.restoreFactoryDefaults();
        m_leaderRight.restoreFactoryDefaults();
        m_followerRight.restoreFactoryDefaults();

        m_leaderLeft.setSmartCurrentLimit(60);
        m_followerLeft.setSmartCurrentLimit(60);
        m_leaderRight.setSmartCurrentLimit(60);
        m_followerRight.setSmartCurrentLimit(60);

        m_leaderLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_leaderRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

        if (Constants.IS_ROBOT_BLOSSOM) {
            m_leaderLeft.setInverted(true);
            m_leaderRight.setInverted(false);
        }
        else {
            m_leaderLeft.setInverted(false);
            m_leaderRight.setInverted(true);
        }

        m_followerLeft.follow(m_leaderLeft, false);
        m_followerRight.follow(m_leaderRight, false);

        m_drive = new DifferentialDrive(m_leaderLeft, m_leaderRight);


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

        m_poseEstimator = new DifferentialDrivePoseEstimator(
            K_DRIVE_KINEMATICS, m_gyro.getRotation2d(), 0.0, 0.0, new Pose2d());

        m_networkTableEntries.addDouble("Left Position", () -> Units.metersToInches(m_leftEncoder.getPosition()));
        m_networkTableEntries.addDouble("Left Velocity", () -> Units.metersToInches(m_leftEncoder.getVelocity()));
        m_networkTableEntries.addDouble("Right Position", () -> Units.metersToInches(m_rightEncoder.getPosition()));
        m_networkTableEntries.addDouble("Rigth Velocity", () -> Units.metersToInches(m_rightEncoder.getVelocity()));

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("Chassis Subsystem");
        m_trajectoryLeftWheelSpeedGoal = loggingTable.getEntry("Trajectory Velocity Left Wheel Speed Goal");
        m_trajectoryRightWheelSpeedGoal = loggingTable.getEntry("Trajectory Velocity Right Wheel Speed Goal");

        m_leaderLeftMotorErrorAlert = new SparkMaxAlerts(m_leaderLeft, "left chassis motor ");
        m_followerLeftMotorErrorAlert = new SparkMaxAlerts(m_followerLeft, "left chassis motor ");
        m_leaderRightMotorErrorAlert = new SparkMaxAlerts(m_leaderRight, "right chassis motor ");
        m_followerRightMotorErrorAlert = new SparkMaxAlerts(m_followerRight, "right chassis motor ");

        PPRamseteCommand.setLoggingCallbacks(
            m_field::setTrajectory,
            m_field::setTrajectorySetpoint,
            this::logTrajectoryChassisSetpoint,
            this::logTrajectoryErrors
        );

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



    private void logTrajectoryChassisSetpoint(ChassisSpeeds chassisSpeeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = K_DRIVE_KINEMATICS.toWheelSpeeds(chassisSpeeds);

        m_trajectoryVelocitySetpointX.setNumber(chassisSpeeds.vxMetersPerSecond);
        m_trajectoryVelocitySetpointY.setNumber(chassisSpeeds.vyMetersPerSecond);
        m_trajectoryVelocitySetpointAngle.setNumber(chassisSpeeds.omegaRadiansPerSecond);

        m_trajectoryLeftWheelSpeedGoal.setNumber(Units.metersToInches(wheelSpeeds.leftMetersPerSecond));
        m_trajectoryRightWheelSpeedGoal.setNumber(Units.metersToInches(wheelSpeeds.rightMetersPerSecond));
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        if (Constants.IS_ROBOT_BLOSSOM) {
            return new RevPidPropertyBuilder("Chassis", true, pidController, 0)
                .addP(0.6)
                .addI(0)
                .addD(0)
                .addFF(0.215)
                // .addMaxVelocity(0)
                // .addMaxAcceleration(0)
                .build();
        }
        else {
            return new RevPidPropertyBuilder("Chassis", false, pidController, 0)
                .addP(0)
                .addI(0)
                .addD(0)
                .addFF(.22)
                // .addMaxVelocity(2)
                // .addMaxAcceleration(0)
                .build();
        }
    }

    @Override
    public void periodic() {

        updateOdometry();

        m_field.setOdometry(m_odometry.getPoseMeters());
        m_field.setPoseEstimate(m_poseEstimator.getEstimatedPosition());

        m_leftPIDProperties.updateIfChanged();
        m_rightPIDProperties.updateIfChanged();
        m_turnAnglePIDProperties.updateIfChanged();
        m_turnAnglePIDFFProperty.updateIfChanged();
        m_turnPidAllowableError.updateIfChanged();

        m_leaderLeftMotorErrorAlert.checkAlerts();
        m_followerLeftMotorErrorAlert.checkAlerts();
        m_leaderRightMotorErrorAlert.checkAlerts();
        m_followerRightMotorErrorAlert.checkAlerts();
        m_pigeonAlerts.checkAlerts();
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    @Override
    public Pose2d getPose() {
        return m_poseEstimator.getEstimatedPosition();
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        m_leftPIDcontroller.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0);
        m_rightPIDcontroller.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0);
    }

    @Override
    public void stop() {
        setArcadeDrive(0, 0);
    }

    public void setArcadeDrive(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }

    public void setCurvatureDrive(double speed, double steer, boolean allowTurnInPlace) {
        m_drive.curvatureDrive(speed, steer, allowTurnInPlace);
    }

    @Override
    public void turnToAngle(double angleGoal) {
        SmartDashboard.putNumber("goal angle chassis pid", angleGoal);
        double steerVoltage = m_turnAnglePID.calculate(m_odometry.getPoseMeters().getRotation().getDegrees(), angleGoal);
        steerVoltage += m_turnAnglePIDFFProperty.calculate(m_turnAnglePID.getSetpoint().velocity);

        m_gyroAngleGoalVelocityEntry.setNumber(m_turnAnglePID.getSetpoint().velocity);


        if (turnPIDIsAtAngle()) {
            steerVoltage = 0;
        }

        m_leaderRight.setVoltage(steerVoltage);
        m_leaderLeft.setVoltage(-steerVoltage);
        m_drive.feed();
    }

    @Override
    public void autoEngage() {
        double speed;
        if (Constants.IS_ROBOT_BLOSSOM) {
            speed = getPitch() * AUTO_ENGAGE_KP.getValue();
        }
        else {
            speed = -getPitch() * AUTO_ENGAGE_KP.getValue();
        }

        if (getPitch() > PITCH_LOWER_LIMIT && getPitch() < PITCH_UPPER_LIMIT) {
            setArcadeDrive(0, 0);

        } else if (getPitch() > 0) {
            if (speed < -0.35) {
                speed = -0.35;
            }
            setArcadeDrive(speed, 0);

        } else if (getPitch() < 0) {
            if (speed > 0.35) {
                speed = 0.35;
            }
            setArcadeDrive(speed, 0);
        }
        m_tryingToEngage = true;
    }



    private void updateOdometry() {
        m_poseEstimator.update(
            m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_odometry.update(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        if (DriverStation.isTeleop()) {
            for (Vision vision : m_cameras) {
                updateCameraEstimate(vision);
            }
        }
    }

    private void updateCameraEstimate(Vision vision) {
        //NEW ODOMETRY
        Optional<EstimatedRobotPose> result =
            vision.getEstimatedGlobalPose(m_poseEstimator.getEstimatedPosition());
        if (result.isPresent()) {
            EstimatedRobotPose camPose = result.get();
            Pose2d pose2d = camPose.estimatedPose.toPose2d();
            m_poseEstimator.addVisionMeasurement(pose2d, camPose.timestampSeconds);
        }
    }

    @Override
    public void resetOdometry(Pose2d pose2d) {
        m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        m_poseEstimator.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        //System.out.println("Reset Odometry was called");
    }

    @Override
    public void clearStickyFaults() {
        m_leaderLeft.clearFaults();
        m_leaderRight.clearFaults();
        m_followerLeft.clearFaults();
        m_followerRight.clearFaults();
    }

    @Override
    public CommandBase createDriveToPointNoFlipCommand(Pose2d start, Pose2d end, boolean reverse) {
        PathPlannerTrajectory traj1 = PathPlanner.generatePath(
            new PathConstraints(Units.inchesToMeters(m_onTheFlyMaxVelocity.getValue()), Units.inchesToMeters(m_onTheFlyMaxAcceleration.getValue())),
            reverse,
            new PathPoint(start.getTranslation(), getPose().getRotation()),
            new PathPoint(end.getTranslation(), end.getRotation())
        );

        return new PPRamseteCommand(
            traj1,
            this::getPose, // Pose supplier
            new RamseteController(),
            K_DRIVE_KINEMATICS, // DifferentialDriveKinematics
            this::smartVelocityControl, // DifferentialDriveWheelSpeeds supplier
            false, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
            this // Requires this drive subsystem
        ).finallyDo((interrupted) -> {
            m_field.clearTrajectory();
        });

    }

    public void drivetrainToBrakeMode() {
        m_leaderLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_followerLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_leaderRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_followerRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

    }

    public void drivetrainToCoastMode() {
        m_leaderLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerLeft.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_leaderRight.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_followerRight.setIdleMode(CANSparkMax.IdleMode.kCoast);

    }

    @Override
    protected void lockDriveTrain() {
        drivetrainToBrakeMode();
    }

    @Override
    protected void unlockDriveTrain() {
        drivetrainToCoastMode();
    }

    ////////////////////
    // Command Factories
    ////////////////////

    public CommandBase commandChassisVelocity() {
        return this.runEnd(
            () -> smartVelocityControl(Units.inchesToMeters(m_maxVelocity.getValue()), Units.inchesToMeters(m_maxVelocity.getValue())),
            this::stop).withName("Chassis: Tune Velocity");
    }


    @Override
    public CommandBase createSyncOdometryWithPoseEstimatorCommand() {
        return runOnce(() ->  m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), m_poseEstimator.getEstimatedPosition()))
            .withName("Sync Odometry /w Pose");
    }

    @Override
    public CommandBase createSelfTestMotorsCommand() {
        return new SequentialCommandGroup(createIsLeftMotorMoving(), createIsRightMotorMoving());
    }

    @Override
    protected BaseAutoBuilder createPathPlannerAutoBuilder(Map<String, Command> eventMap, Consumer<Pose2d> poseSetter) {
        return new RamseteAutoBuilder(
            this::getPose, // Pose supplier
            poseSetter,
            new RamseteController(),
            K_DRIVE_KINEMATICS, // DifferentialDriveKinematics
            this::smartVelocityControl, // DifferentialDriveWheelSpeeds supplier
            eventMap,
            true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
            this);
    }

    ////////////////
    // Checklists
    ////////////////
    public CommandBase createIsLeftMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_leaderLeft, "Chassis: Leader left motor", 1.0);
    }

    public CommandBase createIsRightMotorMoving() {
        return new SparkMaxMotorsMoveChecklist(this, m_leaderRight, "Chassis: Leader right motor", 1.0);
    }



}
