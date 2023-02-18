package com.gos.chargedup.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.gos.chargedup.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.lib.rev.SparkMaxAlerts;
import com.gos.lib.rev.checklists.SparkMaxMotorsMoveChecklist;
import com.pathplanner.lib.auto.RamseteAutoBuilder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.frc2023.FieldConstants;
import org.photonvision.EstimatedRobotPose;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

import java.util.Map;
import java.util.Optional;


public class ChassisSubsystem extends SubsystemBase {
    private static final GosDoubleProperty AUTO_ENGAGE_KP = new GosDoubleProperty(false, "Chassis auto engage kP", .02);

    private static final double PITCH_LOWER_LIMIT = -3.0;
    private static final double PITCH_UPPER_LIMIT = 3.0;

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
    private final WPI_Pigeon2 m_gyro;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;

    //Field
    private final Field2d m_field;

    //SIM
    private DifferentialDrivetrainSimWrapper m_simulator;

    private final Vision m_vision;

    private final DifferentialDrivePoseEstimator m_poseEstimator;


    private final SparkMaxPIDController m_leftPIDcontroller;
    private final SparkMaxPIDController m_rightPIDcontroller;

    private final PidProperty m_leftPIDProperties;
    private final PidProperty m_rightPIDProperties;

    private final NetworkTableEntry m_gyroAngleDegEntry;
    private final NetworkTableEntry m_leftEncoderPosition;
    private final NetworkTableEntry m_leftEncoderVelocity;
    private final NetworkTableEntry m_rightEncoderPosition;
    private final NetworkTableEntry m_rightEncoderVelocity;


    private final GosDoubleProperty m_maxVelocity = new GosDoubleProperty(false, "Max Chassis Velocity", 60);

    private final SparkMaxAlerts m_leaderLeftMotorErrorAlert;
    private final SparkMaxAlerts m_followerLeftMotorErrorAlert;
    private final SparkMaxAlerts m_leaderRightMotorErrorAlert;
    private final SparkMaxAlerts m_followerRightMotorErrorAlert;

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

        m_gyro = new WPI_Pigeon2(Constants.PIGEON_PORT);
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

        m_vision = new PhotonVisionSubsystem();
        // m_vision = new LimelightVisionSubsystem();

        NetworkTable loggingTable = NetworkTableInstance.getDefault().getTable("ChassisSubsystem");
        m_gyroAngleDegEntry = loggingTable.getEntry("Gyro Angle (deg)");

        m_leftEncoderPosition = loggingTable.getEntry("Left Position");
        m_leftEncoderVelocity = loggingTable.getEntry("Left Velocity");
        m_rightEncoderPosition = loggingTable.getEntry("Right Position");
        m_rightEncoderVelocity = loggingTable.getEntry("Right Velocity");

        m_leaderLeftMotorErrorAlert = new SparkMaxAlerts(m_leaderLeft, "left chassis motor ");
        m_followerLeftMotorErrorAlert = new SparkMaxAlerts(m_followerLeft, "left chassis motor ");
        m_leaderRightMotorErrorAlert = new SparkMaxAlerts(m_leaderRight, "right chassis motor ");
        m_followerRightMotorErrorAlert = new SparkMaxAlerts(m_followerRight, "right chassis motor ");

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

    public void nodeCount(FieldConstants fieldConstants) {
        for (int i = 0; i < FieldConstants.Grids.NODE_ROW_COUNT; i++) {
            FieldConstants.Grids.LOW_TRANSLATIONS[i] = new Translation2d(FieldConstants.Grids.LOW_X, FieldConstants.Grids.NODE_FIRST_Y + FieldConstants.Grids.NODE_SEPARATION_Y * i);
        }
    }

    public double findingClosestNode(double yPositionButton) {
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
        return new RevPidPropertyBuilder("Chassis", false, pidController, 0)
            .addP(0) //this needs to be tuned!
            .addI(0)
            .addD(0)
            .addFF(.22)
            .addMaxVelocity((Units.inchesToMeters(2)))
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
        m_leftEncoderPosition.setNumber(Units.metersToInches(m_leftEncoder.getPosition()));
        m_leftEncoderVelocity.setNumber(Units.metersToInches(m_leftEncoder.getVelocity()));
        m_rightEncoderPosition.setNumber(Units.metersToInches(m_rightEncoder.getPosition()));
        m_rightEncoderVelocity.setNumber(Units.metersToInches(m_rightEncoder.getVelocity()));
        SmartDashboard.putNumber("Position values: X", Units.metersToInches(getPose().getX()));
        SmartDashboard.putNumber("Position values: Y", Units.metersToInches(getPose().getY()));
        SmartDashboard.putNumber("Position values: theta", getPose().getRotation().getDegrees());
        SmartDashboard.putNumber("Chassis Pitch", getPitch());

        m_leaderLeftMotorErrorAlert.checkAlerts();
        m_followerLeftMotorErrorAlert.checkAlerts();
        m_leaderRightMotorErrorAlert.checkAlerts();
        m_followerRightMotorErrorAlert.checkAlerts();
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

    public void autoEngage() {
        double speed = -getPitch() * AUTO_ENGAGE_KP.getValue();

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
    }

    public CommandBase createAutoEngageCommand() {
        return this.run(this::autoEngage);
    }


    //NEW ODOMETRY
    public void updateOdometry() {
        //OLD ODOMETRY
        m_odometry.update(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

        //NEW ODOMETRY
        m_poseEstimator.update(
                    m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        Optional<EstimatedRobotPose> result =
            m_vision.getEstimatedGlobalPose(m_poseEstimator.getEstimatedPosition());
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
        return this.runOnce(() -> resetOdometry(pose2d))
            .withName("Reset Odometry [" + pose2d.getX() + ", " + pose2d.getY() + ", " + pose2d.getRotation().getDegrees() + "]");
    }

    public CommandBase syncOdometryWithPoseEstimator() {
        return runOnce(() ->  m_odometry.resetPosition(m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), m_poseEstimator.getEstimatedPosition()))
            .withName("Sync Odometry /w Pose");
    }

    public RamseteAutoBuilder ramseteAutoBuilder(Map<String, Command> eventMap) {
        return new RamseteAutoBuilder(
            this::getPose, // Pose supplier
            this::resetOdometry,
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
