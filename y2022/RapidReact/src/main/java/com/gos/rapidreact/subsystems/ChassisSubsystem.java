package com.gos.rapidreact.subsystems;


import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.PropertyManager;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;



public class ChassisSubsystem extends SubsystemBase {

    //TODO: change constants to match this year's robot
    private static final double WHEEL_DIAMETER = Units.inchesToMeters(4.0);
    private static final double GEAR_RATIO = 5;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private static final PropertyManager.IProperty<Double> TO_XY_TURN_PID = new PropertyManager.DoubleProperty("To XY Turn PID", 0);
    private static final PropertyManager.IProperty<Double> TO_XY_DISTANCE_PID = new PropertyManager.DoubleProperty("To XY Distance PID", 0);

    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;

    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final SparkMaxPIDController m_leftPidController;
    private final SparkMaxPIDController m_rightPidController;

    private final PidProperty m_leftProperties;
    private final PidProperty m_rightProperties;

    private final DifferentialDrive m_drive;

    //odometry
    private final DifferentialDriveOdometry m_odometry;
    private final WPI_PigeonIMU m_gyro;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;
    private DifferentialDrivetrainSimWrapper m_simulator;
    private final Field2d m_field;

    //constants for trajectory
    public static final double ksVolts = 0.179;
    public static final double kvVoltSecondsPerMeter = 0.0653;
    public static final double kaVoltSecondsSquaredPerMeter = 0.00754;
    public static final double kvVoltSecondsPerRadian = 2.5;
    public static final double kaVoltSecondsSquaredPerRadian = 0.3;
    public static final double maxVoltage = 10;

    public static final double kTrackwidthMeters = 1.1554881713809029;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);


    public static final double DEFAULT_VELOCITY = Units.inchesToMeters(72);
    public static final double DEFAULT_ACCELERATION = Units.inchesToMeters(60);

    public ChassisSubsystem() {
        m_leaderLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderLeft.restoreFactoryDefaults();
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerLeft.restoreFactoryDefaults();
        m_leaderRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderRight.restoreFactoryDefaults();
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerRight.restoreFactoryDefaults();

        m_drive = new DifferentialDrive(m_leaderLeft, m_leaderRight);

        m_rightEncoder = m_leaderRight.getEncoder();
        m_leftEncoder = m_leaderLeft.getEncoder();

        m_leftPidController = m_leaderLeft.getPIDController();
        m_rightPidController = m_leaderRight.getPIDController();

        m_leftEncoder.setPositionConversionFactor(ENCODER_CONSTANT);
        m_rightEncoder.setPositionConversionFactor(ENCODER_CONSTANT);

        m_leftEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);
        m_rightEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);

        m_gyro = new WPI_PigeonIMU(Constants.PIGEON_PORT);

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));

        m_field = new Field2d();

        CANSparkMax.IdleMode idleMode = CANSparkMax.IdleMode.kCoast;
        m_leaderLeft.setIdleMode(idleMode);
        m_followerLeft.setIdleMode(idleMode);
        m_leaderRight.setIdleMode(idleMode);
        m_followerRight.setIdleMode(idleMode);


        m_leaderLeft.setInverted(false);
        m_leaderRight.setInverted(true);

        m_followerLeft.follow(m_leaderLeft, false);
        m_followerRight.follow(m_leaderRight, false);

        // Smart Motion stuff
        double kp = 0.01000;
        double ki = 0;
        double kd = 0;
        double kff = 0.005800;
        boolean lockConstants = false;
        double minVel = 0; // m/sec
        double maxVel = Units.inchesToMeters(72); // m/sec
        double maxAcc = Units.inchesToMeters(144); // m/sec/sec
        double allowedErr = 0;
        double kMaxOutput = 1;
        double kMinOutput = -1;
        int smartMotionSlot = 0;

        m_leftProperties = new RevPidPropertyBuilder("Chassis", lockConstants, m_leftPidController, 0)
            .addP(kp)
            .addI(ki)
            .addD(kd)
            .addFF(kff)
            .addMaxVelocity(maxVel)
            .addMaxAcceleration(maxAcc)
            .build();
        m_rightProperties = new RevPidPropertyBuilder("Chassis", lockConstants, m_rightPidController, 0)
            .addP(kp)
            .addI(ki)
            .addD(kd)
            .addFF(kff)
            .addMaxVelocity(maxVel)
            .addMaxAcceleration(maxAcc)
            .build();

        m_leftProperties.updateIfChanged(true);
        m_rightProperties.updateIfChanged(true);

        if (RobotBase.isSimulation()) {
            DifferentialDrivetrainSim drivetrainSim = DifferentialDrivetrainSim.createKitbotSim(
                DifferentialDrivetrainSim.KitbotMotor.kDualCIMPerSide,
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

        SmartDashboard.putData(m_field);
    }

    @Override
    public void periodic() {
        m_odometry.update(Rotation2d.fromDegrees(getYawAngle()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());
        SmartDashboard.putData(m_field);
        SmartDashboard.putNumber("Left Dist (inches)", Units.metersToInches(m_leftEncoder.getPosition()));
        SmartDashboard.putNumber("Right Dist (inches)", Units.metersToInches(m_rightEncoder.getPosition()));
        SmartDashboard.putNumber("Left Velocity (inches)", Units.metersToInches(m_leftEncoder.getVelocity()));
        SmartDashboard.putNumber("Right Velocity (inches)", Units.metersToInches(m_rightEncoder.getVelocity()));

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
    }

    public void resetInitialOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getYawAngle()));
        m_simulator.resetOdometry(pose);
    }

    public double getAverageEncoderDistance() {
        return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void stop() {
        m_drive.stopMotor();
        System.out.println("Stopping motors");
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity) {
        // System.out.println("Driving velocity");
        m_leftPidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity);
        m_rightPidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity);
        m_drive.feed();

        System.out.println("Left Velocity" + leftVelocity + ", Right Velocity" + rightVelocity);
    }

    public double getLeftEncoderSpeed() {
        return m_leftEncoder.getVelocity();
    }

    public double getRightEncoderSpeed() {
        return m_rightEncoder.getVelocity();
    }

    public double getHeading() {
        return -m_gyro.getYaw();
        // return 0;
    }

    public void resetOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
        m_simulator.resetOdometry(pose);
    }

    public void setPositionMeters(double x, double y, double angle) {
        resetOdometry(new Pose2d(x, y, Rotation2d.fromDegrees(angle)));
    }

    public double getYawAngle() {
        return -m_gyro.getYaw();
    }

    public void setArcadeDrive(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }

    public boolean goToCargo(double xCoordinate, double yCoordinate) {

        double xError;
        double yError; //gets distance to the coordinate
        double angleError;
        double xCurrent = m_odometry.getPoseMeters().getX();
        double yCurrent = m_odometry.getPoseMeters().getY();
        double angleCurrent = m_odometry.getPoseMeters().getRotation().getRadians();

        double hDistance; //gets distance of the hypotenuse
        double angle;

        xError = xCoordinate - xCurrent;
        yError = yCoordinate - yCurrent;
        hDistance = Math.sqrt((xError * xError) + (yError * yError));
        angle = Math.atan2(yError, xError);
        angleError = angle - angleCurrent;

        System.out.println("xError   " + xError);
        System.out.println("yError   " + yError);
        return driveAndTurnPID(hDistance, angleError);
    }

    public boolean driveAndTurnPID(double distance, double angle) {
        double allowableDistanceError = Units.inchesToMeters(12.0);
        double allowableAngleError = Units.degreesToRadians(5.0);

        double speed = TO_XY_DISTANCE_PID.getValue() * distance; //p * error pid
        double steer = TO_XY_TURN_PID.getValue() * angle;

        // System.out.println("speed   " + speed);
        // System.out.println("steer   " + steer);
        // System.out.println("hDistance   " + hDistance);
        // System.out.println("angle   " + Math.toDegrees(angleError));
        // System.out.println();

        setArcadeDrive(speed, steer);
        return Math.abs(distance) < allowableDistanceError && Math.abs(angle) < allowableAngleError;
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
