package com.gos.rapidreact.subsystems;


import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.gos.lib.properties.HeavyDoubleProperty;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.properties.PropertyManager;
import com.gos.lib.properties.WpiPidPropertyBuilder;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.gos.rapidreact.subsystems.sim.LimelightSim;
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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
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

    private static final double WHEEL_DIAMETER = Units.inchesToMeters(6.0);
    private static final double GEAR_RATIO = 40.0 / 12.0 * 40.0 / 14.0;
    private static final double ENCODER_CONSTANT = (1.0 / GEAR_RATIO) * WHEEL_DIAMETER * Math.PI;

    private static final PropertyManager.IProperty<Double> TO_XY_DISTANCE_SPEED = PropertyManager.createDoubleProperty(false, "To XY Dist Speed", 0);
    public static final PropertyManager.IProperty<Double> TO_XY_MAX_DISTANCE = PropertyManager.createDoubleProperty(false, "To XY Max Dist", 4);

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

    private final PIDController m_turnAnglePID;
    private final PidProperty m_turnAnglePIDProperties;

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
    public static final double KV_VOLT_SECONDS_PER_RADIAN = 1.5066;
    public static final double KA_VOLT_SECONDS_SQUARED_PER_RADIAN = 0.08475;
    public static final double KS_VOLTS_STATIC_FRICTION_TURNING = 1.48;
    public static final double MAX_VOLTAGE = 10;

    public static final double K_TRACKWIDTH_METERS = 1.8603;
    public static final DifferentialDriveKinematics K_DRIVE_KINEMATICS =
        new DifferentialDriveKinematics(K_TRACKWIDTH_METERS);

    // Sim
    private DifferentialDrivetrainSimWrapper m_simulator;
    private LimelightSim m_limelightSim;

    // Logging
    private final NetworkTableEntry m_turnToAngleVoltageEntry;
    private final NetworkTableEntry m_turnToAngleGoalEntry;
    private final NetworkTableEntry m_goToCargoTurnSpeedEntry;
    private final NetworkTableEntry m_goToCargoForwardSpeedEntry;
    private final NetworkTableEntry m_gyroAngleEntry;
    private final NetworkTableEntry m_gyroAngleRateEntry;

    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NcssCount"})
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

        m_turnAnglePID = new PIDController(0, 0, 0);
        if (DriverStation.isTeleop()) {
            m_turnAnglePID.setTolerance(ShooterLimelightSubsystem.ALLOWABLE_TELEOP_ANGLE_ERROR, 5);
        }
        if (DriverStation.isAutonomous()) {
            m_turnAnglePID.setTolerance(ShooterLimelightSubsystem.ALLOWABLE_AUTO_ANGLE_ERROR, 5);
        }
        m_turnAnglePID.enableContinuousInput(-180, 180);
        m_turnAnglePIDProperties = new WpiPidPropertyBuilder("Chassis to angle", false, m_turnAnglePID)
            .addP(0)
            .addI(0)
            .addD(0)
            .build();
        //p 0.32, d 0.21

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

        NetworkTable chassisTable = NetworkTableInstance.getDefault().getTable("Chassis");

        NetworkTable turnToAngleTable = chassisTable.getSubTable("TurnToAngle");
        m_turnToAngleVoltageEntry = turnToAngleTable.getEntry("Voltage");
        m_turnToAngleGoalEntry = turnToAngleTable.getEntry("Goal");

        NetworkTable goToCargoTable = chassisTable.getSubTable("GoToCargo");
        m_goToCargoTurnSpeedEntry = goToCargoTable.getEntry("TurnSpeed");
        m_goToCargoForwardSpeedEntry = goToCargoTable.getEntry("ForwardSpeed");

        NetworkTable odometryTable = chassisTable.getSubTable("Odometry");
        m_gyroAngleEntry = odometryTable.getEntry("Angle (deg)");
        m_gyroAngleRateEntry = odometryTable.getEntry("Angle (dps)");
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

        m_field.setRobotPose(getPose());
        m_coordinateGuiPublisher.publish(getPose());
        m_gyroAngleEntry.setNumber(getYawAngle());
        m_gyroAngleRateEntry.setNumber(m_gyro.getRate());

        m_leftProperties.updateIfChanged();
        m_rightProperties.updateIfChanged();
        m_toCargoPIDProperties.updateIfChanged();
        m_openLoopRampRateProperty.updateIfChanged();
        m_turnAnglePIDProperties.updateIfChanged();

        SmartDashboard.putNumber("chassis dist", getLeftEncoderDistance());
    }

    public void resetInitialOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getYawAngle()));

        if (RobotBase.isSimulation()) {
            m_simulator.resetOdometry(pose);
        }
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void smartVelocityControl(double leftVelocity, double rightVelocity, double leftAcceleration, double rightAcceleration) {
        double staticFrictionLeft = KS_VOLTS_FORWARD * Math.signum(leftVelocity); //arbFeedforward
        double staticFrictionRight = KS_VOLTS_FORWARD * Math.signum(rightVelocity);
        double accelerationLeft = KA_VOLT_SECONDS_SQUARED_PER_METER * Math.signum(leftAcceleration);
        double accelerationRight = KA_VOLT_SECONDS_SQUARED_PER_METER * Math.signum(rightAcceleration);
        m_leftPidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0, staticFrictionLeft + accelerationLeft);
        m_rightPidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0, staticFrictionRight + accelerationRight);
        m_drive.feed();
    }

    public void trapezoidMotionControl(double leftDistance, double rightDistance) {
        double leftError = leftDistance - getLeftEncoderDistance();
        double rightError = rightDistance - getRightEncoderDistance();
        double staticFrictionLeft = KS_VOLTS_FORWARD * Math.signum(leftError);
        double staticFrictionRight = KS_VOLTS_FORWARD * Math.signum(rightError);
        m_leftPidController.setReference(leftDistance, CANSparkMax.ControlType.kSmartMotion, 0, staticFrictionLeft);
        m_rightPidController.setReference(rightDistance, CANSparkMax.ControlType.kSmartMotion, 0, staticFrictionRight);
        m_drive.feed();
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

    public double getOdometryAngle() {
        return m_odometry.getPoseMeters().getRotation().getDegrees();
    }

    public double getWrappedYawAngle() {
        return m_odometry.getPoseMeters().getRotation().getDegrees();
    }

    public void setArcadeDrive(double speed, double steer) {
        m_drive.arcadeDrive(speed, steer);
    }

    public boolean goToCargo(double xCoordinate, double yCoordinate) {

        double xCurrent = m_odometry.getPoseMeters().getX();
        double yCurrent = m_odometry.getPoseMeters().getY();
        double angleCurrent = m_odometry.getPoseMeters().getRotation().getRadians();

        double xError = xCoordinate - xCurrent;
        double yError = yCoordinate - yCurrent;
        double hDistance = Math.sqrt((xError * xError) + (yError * yError));
        double angle = Math.atan2(yError, xError);
        double angleError = angle - angleCurrent;

        return driveAndTurnPID(hDistance, angleError);
    }

    public boolean driveAndTurnPID(double distance, double angle) {
        double allowableDistanceError = Units.inchesToMeters(12.0); //for go to cargo
        double allowableAngleError = Units.degreesToRadians(5.0);

        // double speed = TO_XY_DISTANCE_PID.getValue() * distance; //p * error pid
        double steer = m_toCargoPID.calculate(0, angle);

        // HACK - Always use the same speed
        double speed = TO_XY_DISTANCE_SPEED.getValue();


        m_goToCargoTurnSpeedEntry.setNumber(steer);
        m_goToCargoForwardSpeedEntry.setNumber(speed);

        setArcadeDrive(speed, steer);
        return Math.abs(distance) < allowableDistanceError && Math.abs(angle) < allowableAngleError;
    }

    /**
     *
     * @param angleGoal In degrees
     * @return if at allowable angle
     */
    public boolean turnPID(double angleGoal) { //for shooter limelight
        double steerVoltage = m_turnAnglePID.calculate(getOdometryAngle(), angleGoal);
        steerVoltage += Math.copySign(KS_VOLTS_STATIC_FRICTION_TURNING, steerVoltage);

        if (m_turnAnglePID.atSetpoint()) {
            steerVoltage = 0;
        }

        m_leaderRight.setVoltage(steerVoltage);
        m_leaderLeft.setVoltage(-steerVoltage);
        m_drive.feed();

        m_turnToAngleVoltageEntry.setNumber(steerVoltage);
        m_turnToAngleGoalEntry.setNumber(angleGoal);

        return m_turnAnglePID.atSetpoint();

    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
        m_limelightSim.update(m_odometry.getPoseMeters());
    }
}
