package com.gos.chargedup.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.gos.chargedup.AllianceFlipper;
import com.gos.chargedup.Constants;
import com.gos.chargedup.GosField;
import com.gos.chargedup.RectangleInterface;
import com.gos.lib.ctre.PigeonAlerts;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.HeavyDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.WpiProfiledPidPropertyBuilder;
import com.gos.lib.properties.feedforward.SimpleMotorFeedForwardProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.auto.RamseteAutoBuilder;
import com.pathplanner.lib.commands.PPRamseteCommand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
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
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.frc2023.FieldConstants;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@SuppressWarnings("PMD.GodClass")
public class ChassisSubsystem extends SubsystemBase {
    private static final GosDoubleProperty AUTO_ENGAGE_KP = new GosDoubleProperty(false, "Chassis auto engage kP", .025);

    private static final double PITCH_LOWER_LIMIT = -3.0;
    private static final double PITCH_UPPER_LIMIT = 3.0;

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

    private final HeavyDoubleProperty m_turnPidAllowableError;

    //Chassis and motors
    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;
    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final DifferentialDrive m_drive;

    //Odometry
    private final DifferentialDriveOdometry m_odometry;
    private final WPI_Pigeon2 m_gyro;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;

    //Field
    private final GosField m_field;

    //SIM
    private DifferentialDrivetrainSimWrapper m_simulator;

    private final List<Vision> m_cameras;

    private final DifferentialDrivePoseEstimator m_poseEstimator;


    private final SparkMaxPIDController m_leftPIDcontroller;
    private final SparkMaxPIDController m_rightPIDcontroller;

    private final PidProperty m_leftPIDProperties;
    private final PidProperty m_rightPIDProperties;

    private final ProfiledPIDController m_turnAnglePID;
    private final PidProperty m_turnAnglePIDProperties;
    private final SimpleMotorFeedForwardProperty m_turnAnglePIDFFProperty;

    private final NetworkTableEntry m_gyroAngleDegEntry;
    private final NetworkTableEntry m_gyroAngleRateEntry;
    private final NetworkTableEntry m_gyroAngleGoalVelocityEntry;

    private final NetworkTableEntry m_leftEncoderPosition;
    private final NetworkTableEntry m_leftEncoderVelocity;
    private final NetworkTableEntry m_rightEncoderPosition;
    private final NetworkTableEntry m_rightEncoderVelocity;
    private final NetworkTableEntry m_trajectoryErrorX;
    private final NetworkTableEntry m_trajectoryErrorY;
    private final NetworkTableEntry m_trajectoryErrorAngle;
    private final NetworkTableEntry m_trajectoryVelocitySetpointX;
    private final NetworkTableEntry m_trajectoryVelocitySetpointY;
    private final NetworkTableEntry m_trajectoryVelocitySetpointAngle;
    private final NetworkTableEntry m_trajectoryLeftWheelSpeedGoal;
    private final NetworkTableEntry m_trajectoryRightWheelSpeedGoal;


    private final GosDoubleProperty m_maxVelocity = new GosDoubleProperty(true, "Chassis Trajectory Max Velocity", 60);

    private final SparkMaxAlerts m_leaderLeftMotorErrorAlert;
    private final SparkMaxAlerts m_followerLeftMotorErrorAlert;
    private final SparkMaxAlerts m_leaderRightMotorErrorAlert;
    private final SparkMaxAlerts m_followerRightMotorErrorAlert;

    private final RectangleInterface m_communityRectangle1;
    private final RectangleInterface m_communityRectangle2;
    private final RectangleInterface m_loadingRectangle1;
    private final RectangleInterface m_loadingRectangle2;

    private boolean m_tryingToEngage;

    @SuppressWarnings("PMD.NcssCount")
    private final PigeonAlerts m_pigeonAlerts;

    //max velocity and acceleration tuning
    private final GosDoubleProperty m_onTheFlyMaxVelocity = new GosDoubleProperty(true, "Chassis On the Fly Max Acceleration", 48);
    private final GosDoubleProperty m_onTheFlyMaxAcceleration = new GosDoubleProperty(true, "Chassis On the Fly Max Acceleration", 48);

    @SuppressWarnings({"PMD.NcssCount", "PMD.ExcessiveMethodLength"})
    public ChassisSubsystem() {
        m_field = new GosField();
        SmartDashboard.putData(m_field.getSendable());

        m_communityRectangle1 = new RectangleInterface(FieldConstants.Community.INNER_X, FieldConstants.Community.LEFT_Y, FieldConstants.Community.MID_X, FieldConstants.Community.RIGHT_Y, m_field, "BlueCommunityRight");
        m_communityRectangle2 = new RectangleInterface(FieldConstants.Community.MID_X, FieldConstants.Community.MID_Y, FieldConstants.Community.OUTER_X, FieldConstants.Community.RIGHT_Y, m_field, "BlueCommunityLeft");
        m_loadingRectangle1 = new RectangleInterface(FieldConstants.LoadingZone.OUTER_X, FieldConstants.LoadingZone.LEFT_Y, FieldConstants.LoadingZone.MID_X, FieldConstants.LoadingZone.MID_Y, m_field, "BlueLoadingRight");
        m_loadingRectangle2 = new RectangleInterface(FieldConstants.LoadingZone.MID_X, FieldConstants.LoadingZone.LEFT_Y, FieldConstants.LoadingZone.INNER_X, FieldConstants.LoadingZone.RIGHT_Y, m_field, "BlueLoadingLeft");

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

        m_gyro = new WPI_Pigeon2(Constants.PIGEON_PORT);
        m_gyro.configFactoryDefault();
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), 0, 0);

        m_leftPIDcontroller = m_leaderLeft.getPIDController();
        m_rightPIDcontroller = m_leaderRight.getPIDController();

        m_leftPIDProperties = setupPidValues(m_leftPIDcontroller);
        m_rightPIDProperties = setupPidValues(m_rightPIDcontroller);

        m_turnAnglePID = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        m_turnAnglePID.enableContinuousInput(0, 360);
        m_turnAnglePIDProperties = new WpiProfiledPidPropertyBuilder("Chassis to angle", false, m_turnAnglePID)
            .addP(0.4)
            .addI(0)
            .addD(0)
            .addMaxAcceleration(210)
            .addMaxVelocity(210)
            .build();
        m_turnAnglePIDFFProperty = new SimpleMotorFeedForwardProperty("Chassis to angle FF Properties", false)
            .addKs(0.8)
            .addKa(0)
            .addKff(0.027);
        GosDoubleProperty turnPidAllowableErrorProperty = new GosDoubleProperty(false, "Chassis Turn PID Allowable Error", 3.5);
        m_turnPidAllowableError = new HeavyDoubleProperty(m_turnAnglePID::setTolerance, turnPidAllowableErrorProperty);

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

        m_cameras = new ArrayList<>();
        m_cameras.add(new LimelightVisionSubsystem(m_field, "limelight-back"));
        m_cameras.add(new LimelightVisionSubsystem(m_field, "limelight-front"));

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("ChassisSubsystem");
        m_gyroAngleDegEntry = loggingTable.getEntry("Gyro Angle (deg)");
        m_gyroAngleRateEntry = loggingTable.getEntry("Gyro Rate");
        m_gyroAngleGoalVelocityEntry = loggingTable.getEntry("Gyro Goal Velocity");

        m_leftEncoderPosition = loggingTable.getEntry("Left Position");
        m_leftEncoderVelocity = loggingTable.getEntry("Left Velocity");
        m_rightEncoderPosition = loggingTable.getEntry("Right Position");
        m_rightEncoderVelocity = loggingTable.getEntry("Right Velocity");

        m_trajectoryErrorX = loggingTable.getEntry("Trajectory Error X");
        m_trajectoryErrorY = loggingTable.getEntry("Trajectory Error Y");
        m_trajectoryErrorAngle = loggingTable.getEntry("Trajectory Error Angle");
        m_trajectoryVelocitySetpointX = loggingTable.getEntry("Trajectory Velocity Setpoint X");
        m_trajectoryVelocitySetpointY = loggingTable.getEntry("Trajectory Velocity Setpoint Y");
        m_trajectoryVelocitySetpointAngle = loggingTable.getEntry("Trajectory Velocity Setpoint Angle");
        m_trajectoryLeftWheelSpeedGoal = loggingTable.getEntry("Trajectory Velocity Left Wheel Speed Goal");
        m_trajectoryRightWheelSpeedGoal = loggingTable.getEntry("Trajectory Velocity Right Wheel Speed Goal");


        m_leaderLeftMotorErrorAlert = new SparkMaxAlerts(m_leaderLeft, "left chassis motor ");
        m_followerLeftMotorErrorAlert = new SparkMaxAlerts(m_followerLeft, "left chassis motor ");
        m_leaderRightMotorErrorAlert = new SparkMaxAlerts(m_leaderRight, "right chassis motor ");
        m_followerRightMotorErrorAlert = new SparkMaxAlerts(m_followerRight, "right chassis motor ");

        m_pigeonAlerts = new PigeonAlerts(m_gyro);

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
            m_tryingToEngage = false;
        }
    }

    private void logTrajectoryErrors(Translation2d translation2d, Rotation2d rotation2d) {
        m_trajectoryErrorX.setNumber(translation2d.getX());
        m_trajectoryErrorY.setNumber(translation2d.getY());
        m_trajectoryErrorAngle.setNumber(rotation2d.getDegrees());
    }

    private void logTrajectoryChassisSetpoint(ChassisSpeeds chassisSpeeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = K_DRIVE_KINEMATICS.toWheelSpeeds(chassisSpeeds);

        m_trajectoryVelocitySetpointX.setNumber(chassisSpeeds.vxMetersPerSecond);
        m_trajectoryVelocitySetpointY.setNumber(chassisSpeeds.vyMetersPerSecond);
        m_trajectoryVelocitySetpointAngle.setNumber(chassisSpeeds.omegaRadiansPerSecond);

        m_trajectoryLeftWheelSpeedGoal.setNumber(Units.metersToInches(wheelSpeeds.leftMetersPerSecond));
        m_trajectoryRightWheelSpeedGoal.setNumber(Units.metersToInches(wheelSpeeds.rightMetersPerSecond));
    }

    public double findingClosestNodeY(double yPositionButton) {
        double distanceBetweenArrays = FieldConstants.Grids.NODE_SEPARATION_Y * 3;
        double array1 = yPositionButton + 0 * distanceBetweenArrays;
        double array2 = yPositionButton + 1 * distanceBetweenArrays;
        double array3 = yPositionButton + 2 * distanceBetweenArrays;
        double[] mClosestArray = {array1, array2, array3};

        final Pose2d currentRobotPosition = getPose();
        double currentYPos = currentRobotPosition.getY();
        double minDist = Integer.MAX_VALUE;
        double closestNode = minDist;
        for (int i = 0; i < 3; i++) {
            double currentDistance = Math.abs(currentYPos - mClosestArray[i]);
            if (currentDistance < minDist) {
                closestNode = mClosestArray[i];
                minDist = currentDistance;
            }
        }

        return closestNode;
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

        m_gyroAngleDegEntry.setNumber(getYaw());
        if (Constants.IS_ROBOT_BLOSSOM) {
            m_gyroAngleRateEntry.setNumber(-m_gyro.getRate());
        }
        else {
            m_gyroAngleRateEntry.setNumber(m_gyro.getRate());
        }

        m_leftEncoderPosition.setNumber(Units.metersToInches(m_leftEncoder.getPosition()));
        m_leftEncoderVelocity.setNumber(Units.metersToInches(m_leftEncoder.getVelocity()));
        m_rightEncoderPosition.setNumber(Units.metersToInches(m_rightEncoder.getPosition()));
        m_rightEncoderVelocity.setNumber(Units.metersToInches(m_rightEncoder.getVelocity()));
        SmartDashboard.putNumber("Position values: X", Units.metersToInches(getPose().getX()));
        SmartDashboard.putNumber("Position values: Y", Units.metersToInches(getPose().getY()));
        SmartDashboard.putNumber("Position values: theta", getPose().getRotation().getDegrees());
        SmartDashboard.putNumber("Chassis Pitch", getPitch());
        SmartDashboard.putBoolean("In Community", isInCommunityZone());
        SmartDashboard.putBoolean("In Loading", isInLoadingZone());
        SmartDashboard.putNumber("pose angle", m_odometry.getPoseMeters().getRotation().getDegrees());
        SmartDashboard.putBoolean("Field/flip", DriverStation.getAlliance() == DriverStation.Alliance.Red);

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

    public Pose2d getPose() {
        return m_poseEstimator.getEstimatedPosition();
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

    // INTENTIONALLY ROLL, WE ARE NOT BEING PSYCOPATHS I PROMISE
    public double getPitch() {
        return m_gyro.getRoll();
    }

    public double getYaw() {
        return m_gyro.getYaw();
    }

    public void turnPID(double angleGoal) {
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

    public boolean turnPIDIsAtAngle() {
        return m_turnAnglePID.atGoal();
    }

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

    public boolean tryingToEngage() {
        return m_tryingToEngage;
    }

    public CommandBase createAutoEngageCommand() {
        return this.runOnce(this::drivetrainToBrakeMode)
            .andThen(runEnd(this::autoEngage, () -> m_tryingToEngage = false))
            .finallyDo((interrupted) -> drivetrainToCoastMode()).withName("Auto Engage");
    }


    public void updateOdometry() {
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

    public void resetOdometry(Pose2d pose2d) {
        m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        m_poseEstimator.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose2d);
        //System.out.println("Reset Odometry was called");
    }

    public boolean isInCommunityZone() {
        return m_communityRectangle1.pointIsInRect(m_poseEstimator.getEstimatedPosition())
            || m_communityRectangle2.pointIsInRect(m_poseEstimator.getEstimatedPosition());
    }

    public boolean isInLoadingZone() {
        return m_loadingRectangle1.pointIsInRect(m_poseEstimator.getEstimatedPosition())
            || m_loadingRectangle2.pointIsInRect(m_poseEstimator.getEstimatedPosition());
    }

    public boolean canExtendArm() {
        return (this.isInCommunityZone() || this.isInLoadingZone());
    }


    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public CommandBase driveToPoint(Pose2d point, boolean reverse) {
        point = AllianceFlipper.maybeFlip(point);
        System.out.println("flipped point" + point);
        return driveToPointNoFlip(getPose(), point, reverse);
    }

    public CommandBase driveToPointNoFlip(Pose2d start, Pose2d end, boolean reverse) {
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

    public void resetStickyFaultsChassis() {
        m_leaderLeft.clearFaults();
        m_leaderRight.clearFaults();
        m_followerLeft.clearFaults();
        m_followerRight.clearFaults();
    }

    ////////////////////
    // Command Factories
    ////////////////////
    public CommandBase commandChassisVelocity() {
        return this.runEnd(
            () -> smartVelocityControl(Units.inchesToMeters(m_maxVelocity.getValue()), Units.inchesToMeters(m_maxVelocity.getValue())),
            this::stop).withName("Chassis: Tune Velocity");
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


    public CommandBase createResetOdometry(Pose2d pose2d) {
        return this.run(() -> resetOdometry(pose2d))
            .ignoringDisable(true)
            .withName("Reset Odometry [" + pose2d.getX() + ", " + pose2d.getY() + ", " + pose2d.getRotation().getDegrees() + "]");
    }

    public CommandBase syncOdometryWithPoseEstimator() {
        return runOnce(() ->  m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), m_poseEstimator.getEstimatedPosition()))
            .withName("Sync Odometry /w Pose");
    }

    private RamseteAutoBuilder createRamseteAutoBuilder(Map<String, Command> eventMap, Consumer<Pose2d> poseSetter) {
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

    public RamseteAutoBuilder ramseteAutoBuilder(Map<String, Command> eventMap) {
        return createRamseteAutoBuilder(eventMap, this::resetOdometry);
    }

    public RamseteAutoBuilder ramseteAutoBuilderNoPoseReset(Map<String, Command> eventMap) {
        return createRamseteAutoBuilder(eventMap, (Pose2d pose) -> {});
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

    public CommandBase createDriveToPoint(Pose2d point, boolean reverse) {
        return new ProxyCommand(() -> driveToPoint(point, reverse));
    }

    public CommandBase resetPose(PathPlannerTrajectory trajectory, Rotation2d startAngle) {
        return Commands.runOnce(
            () -> {
                PathPlannerTrajectory.PathPlannerState initialState = trajectory.getInitialState();
                initialState =
                        PathPlannerTrajectory.transformStateForAlliance(
                            initialState, DriverStation.getAlliance());
                Pose2d startPose = new Pose2d(initialState.poseMeters.getTranslation(), startAngle);
                resetOdometry(startPose);
            });

    }

    public CommandBase createTurnPID(double angleGoal) {
        return runOnce(() -> m_turnAnglePID.reset(getPose().getRotation().getDegrees()))
            .andThen(this.run(() -> turnPID(angleGoal))
            .until(this::turnPIDIsAtAngle)
            .withName("Chassis to Angle" + angleGoal));
    }
}

