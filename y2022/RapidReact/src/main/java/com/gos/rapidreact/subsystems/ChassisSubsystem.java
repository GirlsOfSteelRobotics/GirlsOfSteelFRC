package com.gos.rapidreact.subsystems;


import com.ctre.phoenix.sensors.WPI_PigeonIMU;
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

    private final SimableCANSparkMax m_leaderLeft;
    private final SimableCANSparkMax m_followerLeft;

    private final SimableCANSparkMax m_leaderRight;
    private final SimableCANSparkMax m_followerRight;

    private final SparkMaxPIDController m_leftPidController;
    private final SparkMaxPIDController m_rightPidController;

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

    public static final double slowSpeedMetersPerSecond = Units.inchesToMeters(48);
    public static final double slowAccelerationMetersPerSecondSquared = Units.inchesToMeters(96);
    public static final double normalSpeedMetersPerSecond = Units.inchesToMeters(72);
    public static final double normalAccelerationMetersPerSecondSquared = Units.inchesToMeters(60);
    public static final double fastSpeedMetersPerSecond = Units.inchesToMeters(120);
    public static final double fastAccelerationMetersPerSecondSquared = Units.inchesToMeters(120);

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public ChassisSubsystem() {
        m_leaderLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerLeft = new SimableCANSparkMax(Constants.DRIVE_LEFT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_leaderRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_LEADER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_followerRight = new SimableCANSparkMax(Constants.DRIVE_RIGHT_FOLLOWER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_drive = new DifferentialDrive(m_leaderLeft, m_leaderRight);

        m_rightEncoder = m_leaderRight.getEncoder();
        m_leftEncoder = m_leaderLeft.getEncoder();

        m_leftPidController = m_leaderLeft.getPIDController();
        m_rightPidController = m_leaderRight.getPIDController();

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
        }

        SmartDashboard.putData(m_field);
    }

    @Override
    public void periodic() {
        m_odometry.update(Rotation2d.fromDegrees(getYawAngle()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
        m_field.setRobotPose(m_odometry.getPoseMeters());
    }

    public void resetInitialOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getYawAngle()));
        m_simulator.resetOdometry(pose);
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

        //System.out.println("Left Velocity" + leftVelocity + ", Right Velocity" + rightVelocity);
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

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}
