package com.gos.codelabs.pid.subsystems;

import com.gos.codelabs.pid.Constants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.module_wrappers.wpi.ADXRS450GyroWrapper;
import org.snobotv2.sim_wrappers.DifferentialDrivetrainSimWrapper;

@SuppressWarnings("PMD.TooManyFields")
public class ChassisSubsystem extends SubsystemBase {

    private static final int PID_SLOT_VELOCITY = 0;
    private static final int PID_SLOT_SMART_MOTION = 1;
    private static final int PID_SLOT_POSITION = 2;

    public static final double DEFAULT_ALLOWABLE_POSITION_ERROR = Units.inchesToMeters(.5);

    private final SimableCANSparkMax m_leftDriveA;
    private final SimableCANSparkMax m_rightDriveA;
    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;
    private final SparkMaxPIDController m_leftPid;
    private final SparkMaxPIDController m_rightPid;
    private final PidProperty m_leftVelocityPidProperty;
    private final PidProperty m_rightVelocityPidProperty;
    private final PidProperty m_leftSmartMotionPidProperty;
    private final PidProperty m_rightSmartMotionPidProperty;
    private final PidProperty m_leftPositionPidProperty;
    private final PidProperty m_rightPositionPidProperty;

    private final DifferentialDrive m_differentialDrive;
    private final DifferentialDriveOdometry m_odometry;
    private final Field2d m_field;

    private final ADXRS450_Gyro m_gyro;

    private DifferentialDrivetrainSimWrapper m_simulator;

    @SuppressWarnings("PMD.CloseResource")
    public ChassisSubsystem() {

        m_leftDriveA = new SimableCANSparkMax(Constants.CAN_CHASSIS_LEFT_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        CANSparkMax leftDriveB = new CANSparkMax(Constants.CAN_CHASSIS_LEFT_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveB.follow(m_leftDriveA);

        m_rightDriveA = new SimableCANSparkMax(Constants.CAN_CHASSIS_RIGHT_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        CANSparkMax rightDriveB = new CANSparkMax(Constants.CAN_CHASSIS_RIGHT_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveB.follow(m_rightDriveA);
        m_rightDriveA.setInverted(true);

        m_leftEncoder = m_leftDriveA.getEncoder();
        m_rightEncoder = m_rightDriveA.getEncoder();
        m_leftPid = m_leftDriveA.getPIDController();
        m_rightPid = m_rightDriveA.getPIDController();
        m_leftVelocityPidProperty = setupVelocityPidConstants(m_leftPid);
        m_rightVelocityPidProperty = setupVelocityPidConstants(m_rightPid);
        m_leftSmartMotionPidProperty = setupSmartMotionPidConstants(m_leftPid);
        m_rightSmartMotionPidProperty = setupSmartMotionPidConstants(m_rightPid);
        m_leftPositionPidProperty = setupPositionPidConstants(m_leftPid);
        m_rightPositionPidProperty = setupPositionPidConstants(m_rightPid);

        m_gyro = new ADXRS450_Gyro();

        m_differentialDrive = new DifferentialDrive(m_leftDriveA, m_rightDriveA);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d());
        m_field = new Field2d();
        SmartDashboard.putData(m_field);

        if (RobotBase.isSimulation()) {
            m_simulator = new DifferentialDrivetrainSimWrapper(
                    Constants.DrivetrainConstants.createSim(),
                    new RevMotorControllerSimWrapper(m_leftDriveA),
                    new RevMotorControllerSimWrapper(m_rightDriveA),
                    RevEncoderSimWrapper.create(m_leftDriveA),
                    RevEncoderSimWrapper.create(m_rightDriveA),
                    new ADXRS450GyroWrapper(m_gyro));
            m_simulator.setRightInverted(false);
        }
    }

    private PidProperty setupVelocityPidConstants(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Chassis.vel", false, pidController, PID_SLOT_VELOCITY)
                .addP(0)
                .addFF(0)
                .build();
    }

    private PidProperty setupPositionPidConstants(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Chassis.pos", false, pidController, PID_SLOT_POSITION)
                .addP(0)
                .addD(0)
                .build();
    }

    private PidProperty setupSmartMotionPidConstants(SparkMaxPIDController pidController) {
        return new RevPidPropertyBuilder("Chassis.sm", false, pidController, PID_SLOT_SMART_MOTION)
                .addP(0)
                .addFF(0)
                .addMaxAcceleration(0)
                .addMaxVelocity(0)
                .build();
    }

    public void setThrottle(double speed) {
        setSpeedAndSteer(speed, 0);
    }

    public void setSpin(double spin) {
        setSpeedAndSteer(0, spin);
    }

    public void setSpeedAndSteer(double speed, double steer) {
        m_differentialDrive.arcadeDrive(speed, steer);
    }

    public void stop() {
        setSpeedAndSteer(0, 0);
    }

    @Override
    public void periodic() {

        m_odometry.update(
                m_gyro.getRotation2d(),
                getLeftDistance(),
                getRightDistance());
        m_field.setRobotPose(m_odometry.getPoseMeters());

        m_leftVelocityPidProperty.updateIfChanged();
        m_rightVelocityPidProperty.updateIfChanged();
        m_leftSmartMotionPidProperty.updateIfChanged();
        m_rightSmartMotionPidProperty.updateIfChanged();
        m_leftPositionPidProperty.updateIfChanged();
        m_rightPositionPidProperty.updateIfChanged();

        SmartDashboard.putNumber("Left Drive", m_leftDriveA.get());
        SmartDashboard.putNumber("Right Drive", m_rightDriveA.get());
        SmartDashboard.putNumber("Left Distance", getLeftDistance());
        SmartDashboard.putNumber("Right Distance", getRightDistance());
        SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
        SmartDashboard.putNumber("Right Velocity", getRightVelocity());
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }

    public double getHeading() {
        return m_gyro.getAngle();
    }

    public double getLeftDistance() {
        return m_leftEncoder.getPosition();
    }

    public double getRightDistance() {
        return m_rightEncoder.getPosition();
    }

    public double getLeftVelocity() {
        return m_leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return m_rightEncoder.getVelocity();
    }

    public double getAverageDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public void driveDistanceCustomControl(double goal) {
        // TODO implement
        m_differentialDrive.feed();
    }

    public void driveDistancePositionControl(double leftDistance, double rightDistance) {
        m_leftPid.setReference(leftDistance, CANSparkMax.ControlType.kPosition, PID_SLOT_POSITION);
        m_rightPid.setReference(rightDistance, CANSparkMax.ControlType.kPosition, PID_SLOT_POSITION);
        m_differentialDrive.feed();

        SmartDashboard.putNumber("Left Position Goal", leftDistance);
        SmartDashboard.putNumber("Right Position Goal", rightDistance);
    }

    public void driveDistanceSmartMotionControl(double leftDistance, double rightDistance) {
        m_leftPid.setReference(leftDistance, CANSparkMax.ControlType.kSmartMotion, PID_SLOT_SMART_MOTION);
        m_rightPid.setReference(rightDistance, CANSparkMax.ControlType.kSmartMotion, PID_SLOT_SMART_MOTION);
        m_differentialDrive.feed();

        SmartDashboard.putNumber("Left SM Goal", leftDistance);
        SmartDashboard.putNumber("Right SM Goal", rightDistance);
    }

    public void driveWithVelocity(double leftVelocity, double rightVelocity) {
        driveWithVelocity(leftVelocity, rightVelocity, 0, 0);
    }

    public void driveWithVelocity(double leftVelocity, double rightVelocity, double leftAccelMpss, double rightAccelMpss) {

        double staticFrictionLeft = Constants.DrivetrainConstants.KS_VOLTS * Math.signum(leftVelocity);
        double staticFrictionRight = Constants.DrivetrainConstants.KS_VOLTS * Math.signum(rightVelocity);
        double accelerationLeft = Constants.DrivetrainConstants.KA_VOLT_SECONDS_SQUARED_PER_METER * Math.signum(leftAccelMpss);
        double accelerationRight = Constants.DrivetrainConstants.KA_VOLT_SECONDS_SQUARED_PER_METER * Math.signum(rightAccelMpss);

        double arbLeft = staticFrictionLeft + accelerationLeft;
        double arbRight = staticFrictionRight + accelerationRight;

        SparkMaxPIDController.ArbFFUnits arbUnit = SparkMaxPIDController.ArbFFUnits.kVoltage;

        m_leftPid.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, PID_SLOT_VELOCITY, arbLeft, arbUnit);
        m_rightPid.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, PID_SLOT_VELOCITY, arbRight, arbUnit);
        m_differentialDrive.feed();

        SmartDashboard.putNumber("Left Velocity Goal", leftVelocity);
        SmartDashboard.putNumber("Right Velocity Goal", rightVelocity);
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
        m_odometry.resetPosition(pose, m_gyro.getRotation2d());
        m_simulator.resetOdometry(pose);
    }
}
