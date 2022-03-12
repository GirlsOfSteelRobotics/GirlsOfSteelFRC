package com.gos.rapidreact.subsystems;


import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.gos.lib.properties.HeavyDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.PropertyManager;
import com.gos.lib.properties.WpiPidPropertyBuilder;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.gos.rapidreact.sim.LimelightSim;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.PIDController;
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
import org.snobotv2.coordinate_gui.RobotPositionPublisher;
import org.snobotv2.module_wrappers.ctre.CtrePigeonImuWrapper;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;



@SuppressWarnings("PMD.TooManyFields")
public class ChassisSubsystem extends SubsystemBase {

    //TODO: change constants to match this year's robot
    private static final double WHEEL_DIAMETER = Units.inchesToMeters(6.0);
    private static final double GEAR_RATIO = 40.0 / 12.0 * 40.0 / 14.0;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private static final PropertyManager.IProperty<Double> TO_XY_TURN_PID = PropertyManager.createDoubleProperty(false, "To XY Turn PID", 0);
    private static final PropertyManager.IProperty<Double> TO_XY_DISTANCE_PID = PropertyManager.createDoubleProperty(false, "To XY Distance PID", 0);

    private static final PropertyManager.IProperty<Double> TO_XY_DISTANCE_SPEED = PropertyManager.createDoubleProperty(false, "To XY Dist Speed", 0);


    private static final PropertyManager.IProperty<Double> TO_HUB_ANGLE_TURN_PID = PropertyManager.createDoubleProperty(false, "To Hub Angle Turn PID", 0);
    private static final PropertyManager.IProperty<Double> TO_HUB_DISTANCE_PID = PropertyManager.createDoubleProperty(false, "To Hub Distance PID", 0);

    private static final PropertyManager.IProperty<Double> DRIVER_OL_RAMP_RATE = PropertyManager.createDoubleProperty(false, "OpenLoopRampRate", 0.5);

    private final HeavyDoubleProperty m_openLoopRampRateProperty;

    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;

    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final SparkMaxPIDController m_leftPidController;
    private final SparkMaxPIDController m_rightPidController;

    private final PidProperty m_leftProperties;
    private final PidProperty m_rightProperties;

    private final PIDController m_toCargoPID;
    private final PidProperty m_toCargoPIDProperties;

    private final DifferentialDrive m_drive;

    //odometry
    private final DifferentialDriveOdometry m_odometry;
    private final WPI_PigeonIMU m_gyro;
    private final RelativeEncoder m_rightEncoder;
    private final RelativeEncoder m_leftEncoder;
    private final RobotPositionPublisher m_coordinateGuiPublisher;
    private final Field2d m_field;

    //constants for trajectory
    public static final double KS_VOLTS_FORWARD = 0.1946;
    public static final double KV_VOLT_SECONDS_PER_METER = 2.6079;
    public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.5049;
    public static final double KV_VOLT_SECONDS_PER_RADIAN = 1.3882;
    public static final double KA_VOLT_SECONDS_SQUARED_PER_RADIAN = 0.071334;
    public static final double KS_VOLTS_STATIC_FRICTION_TURNING = 0.96462;
    public static final double MAX_VOLTAGE = 10;

    public static final double K_TRACKWIDTH_METERS = 1.722;
    public static final DifferentialDriveKinematics K_DRIVE_KINEMATICS =
        new DifferentialDriveKinematics(K_TRACKWIDTH_METERS);

    // Sim
    private DifferentialDrivetrainSimWrapper m_simulator;
    private LimelightSim m_limelightSim;

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

        m_rightEncoder = m_leaderRight.getEncoder();
        m_leftEncoder = m_leaderLeft.getEncoder();

        m_leftPidController = m_leaderLeft.getPIDController();
        m_rightPidController = m_leaderRight.getPIDController();

        m_toCargoPID = new PIDController(0, 0, 0);
        m_toCargoPIDProperties = new WpiPidPropertyBuilder("Chassis to cargo", false, m_toCargoPID)
            .addP(0)
            .addD(0)
            .build();

        m_leftEncoder.setPositionConversionFactor(ENCODER_CONSTANT);
        m_rightEncoder.setPositionConversionFactor(ENCODER_CONSTANT);

        m_leftEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);
        m_rightEncoder.setVelocityConversionFactor(ENCODER_CONSTANT / 60.0);

        m_gyro = new WPI_PigeonIMU(Constants.PIGEON_PORT);

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));
        m_coordinateGuiPublisher = new RobotPositionPublisher();

        m_field = new Field2d();

        m_openLoopRampRateProperty = new HeavyDoubleProperty((double val) -> {
            m_leaderLeft.setOpenLoopRampRate(val);
            m_leaderRight.setOpenLoopRampRate(val);
        }, DRIVER_OL_RAMP_RATE);


        // Smart Motion stuff
        m_leftProperties = setupPidValues(m_leftPidController);
        m_rightProperties = setupPidValues(m_rightPidController);

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

            m_limelightSim = new LimelightSim();
        }

        SmartDashboard.putData(m_field);

        m_leaderLeft.burnFlash();
        m_followerLeft.burnFlash();
        m_leaderRight.burnFlash();
        m_followerRight.burnFlash();
    }

    private PidProperty setupPidValues(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Chassis", false, pidController, 0)
            .addP(0.00003) //0.0012776
            .addI(0)
            .addD(0)
            .addFF(0.215)
            .addMaxVelocity(Units.inchesToMeters(72))
            .addMaxAcceleration(Units.inchesToMeters(144))
            .build();
    }

    @Override
    public void periodic() {
        m_odometry.update(Rotation2d.fromDegrees(getYawAngle()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());
        m_coordinateGuiPublisher.publish(m_odometry.getPoseMeters());
        SmartDashboard.putNumber("Left Dist (inches)", Units.metersToInches(m_leftEncoder.getPosition()));
        SmartDashboard.putNumber("Right Dist (inches)", Units.metersToInches(m_rightEncoder.getPosition()));
        SmartDashboard.putNumber("Gyro (deg)", m_odometry.getPoseMeters().getRotation().getDegrees());
        SmartDashboard.putNumber("Left Velocity (in/s)", Units.metersToInches(m_leftEncoder.getVelocity()));
        SmartDashboard.putNumber("Right Velocity (in/s)", Units.metersToInches(m_rightEncoder.getVelocity()));

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
        m_toCargoPIDProperties.updateIfChanged();
        m_openLoopRampRateProperty.updateIfChanged();
    }

    public void resetInitialOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getYawAngle()));

        if (RobotBase.isSimulation()) {
            m_simulator.resetOdometry(pose);
        }
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
        double staticFrictionLeft = KS_VOLTS_FORWARD * Math.signum(leftVelocity); //arbFeedforward
        double staticFrictionRight = KS_VOLTS_FORWARD * Math.signum(rightVelocity);
        m_leftPidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0, staticFrictionLeft);
        m_rightPidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0, staticFrictionRight);
        m_drive.feed();

        System.out.println("Left Velocity" + leftVelocity + ", Right Velocity" + rightVelocity + " SF: [" + staticFrictionLeft + ", " + staticFrictionRight + "]");
    }

    public void trapezoidMotionControl(double leftDistance, double rightDistance) {
        // System.out.println("Driving velocity");
        double leftError = leftDistance - getLeftEncoderDistance();
        double rightError = rightDistance - getRightEncoderDistance();
        double staticFrictionLeft = KS_VOLTS_FORWARD * Math.signum(leftError);
        double staticFrictionRight = KS_VOLTS_FORWARD * Math.signum(rightError);
        m_leftPidController.setReference(leftDistance, CANSparkMax.ControlType.kSmartMotion, 0, staticFrictionLeft);
        m_rightPidController.setReference(rightDistance, CANSparkMax.ControlType.kSmartMotion, 0, staticFrictionRight);
        m_drive.feed();

        System.out.println("Left Position Goal" + leftDistance + ", Right Position Goal" + rightDistance + " SF: [" + staticFrictionLeft + ", " + staticFrictionRight + "]");
    }

    public double getLeftEncoderSpeed() {
        return m_leftEncoder.getVelocity();
    }

    public double getRightEncoderSpeed() {
        return m_rightEncoder.getVelocity();
    }

    public double getLeftEncoderDistance() {
        return m_leftEncoder.getPosition();
    }

    public double getRightEncoderDistance() {
        return m_rightEncoder.getPosition();
    }

    public double getYawAngle() {
        return m_gyro.getYaw();
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

        System.out.println(yCurrent);
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
        double allowableDistanceError = Units.inchesToMeters(12.0); //for go to cargo
        double allowableAngleError = Units.degreesToRadians(5.0);

        double speed = TO_XY_DISTANCE_PID.getValue() * distance; //p * error pid
        double steer = m_toCargoPID.calculate(0, angle);

        // System.out.println("speed   " + speed);
        // System.out.println("steer   " + steer);
        // System.out.println("hDistance   " + hDistance);
        // System.out.println("angle   " + Math.toDegrees(angleError));
        // System.out.println();

        speed = TO_XY_DISTANCE_SPEED.getValue();

        setArcadeDrive(speed, steer);
        return Math.abs(distance) < allowableDistanceError && Math.abs(angle) < allowableAngleError;
    }

    public boolean turnPID(double angleGoal) { //for shooter limelight
        double allowableAngleError = Units.degreesToRadians(10.0);
        double speed = 0; //should not be moving distance
        double angleCurrent = m_odometry.getPoseMeters().getRotation().getRadians();
        double angleError = angleGoal - angleCurrent;

        double steer = TO_HUB_ANGLE_TURN_PID.getValue() * angleError;
        setArcadeDrive(speed, steer);
        return Math.abs(angleError) < allowableAngleError;
    }

    public boolean distancePID(double currentPosition, double distanceGoal) {
        double allowableDistanceError = Units.inchesToMeters(10.0);
        double error = distanceGoal - currentPosition;

        double speed = error * TO_HUB_DISTANCE_PID.getValue();
        double steer = 0;
        setArcadeDrive(speed, steer);
        return Math.abs(error) < allowableDistanceError;

    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
        m_limelightSim.update(m_odometry.getPoseMeters());
    }
}
