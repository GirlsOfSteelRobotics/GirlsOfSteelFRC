package com.gos.steam_works.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.steam_works.robot.RobotMap;

/**
 *
 */
public class Chassis extends Subsystem {
    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;
    private final WPI_TalonSRX m_driveLeftC;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;
    private final WPI_TalonSRX m_driveRightC;

    private final DifferentialDrive m_robotDrive;

    public Chassis() {
        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
        m_driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C);
        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);
        m_driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveLeftC.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);
        m_driveRightC.setNeutralMode(NeutralMode.Brake);

        m_driveLeftB.follow(m_driveLeftA);
        m_driveLeftC.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);
        m_driveRightC.follow(m_driveRightA);

        setupEncoder(m_driveLeftA);
        setupEncoder(m_driveRightA);

        m_robotDrive = new DifferentialDrive(m_driveLeftA, m_driveRightA);
        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.2);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);

        m_robotDrive.setInvertedMotor(DifferentialDrive.MotorType.kRearLeft, false);
        m_robotDrive.setInvertedMotor(DifferentialDrive.MotorType.kRearRight, false);

        // V per sec; 12 = zero to full speed in 1 second
        m_driveLeftA.configOpenloopRamp(1.0);
        m_driveRightA.configOpenloopRamp(1.0);

        addChild("driveLeftA", m_driveLeftA);
        addChild("driveLeftB", m_driveLeftB);
        addChild("driveLeftC", m_driveLeftC);
        addChild("driveRightA", m_driveRightA);
        addChild("driveRightB", m_driveRightB);
        addChild("driveRightC", m_driveRightC);
    }

    @Override
    public void initDefaultCommand() {

    }

    public WPI_TalonSRX getLeftTalon() {
        return m_driveLeftA;
    }

    public WPI_TalonSRX getRightTalon() {
        return m_driveRightA;
    }

    public void arcadeDrive(double throttle, double turn) {
        m_robotDrive.arcadeDrive(throttle, turn);
    }

    public void tankDrive(double left, double right) {
        m_robotDrive.tankDrive(left, right);
    }

    public final void setupEncoder(WPI_TalonSRX talon) { // only call this on non-follower
        // talons
        // Set Encoder Types
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        talon.setSensorPhase(true);
    }

    public void setupDefaultFPID() { // values work with QuadEncoder for
        setupDefaultFPID(m_driveLeftA);
        setupDefaultFPID(m_driveLeftB);
    }

    private void setupDefaultFPID(WPI_TalonSRX talon) { // values work with QuadEncoder for
        // drive talons
        // PID Values
        talon.setSelectedSensorPosition(0);
        talon.config_kF(0, 0);
        talon.config_kP(0, 0.32); // 0.64 good
        talon.config_kI(0, 0.0);
        talon.config_kD(0, 0.0);
    }

    public void turn(double speed, double curve) {
        m_robotDrive.drive(speed, curve);
    }

    public void stop() {
        m_driveLeftA.set(ControlMode.PercentOutput, 0);
        m_driveRightA.set(ControlMode.PercentOutput, 0);
    }

    public double getLeftPosition() {
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public double getRightPosition() {
        return m_driveRightA.getSelectedSensorPosition();
    }

    public double getLeftVelocity() {
        return m_driveLeftA.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return m_driveRightA.getSelectedSensorVelocity();
    }

    public void setPid(double p, double i, double d, double f) {
        m_driveLeftA.config_kP(0, p);
        m_driveRightA.config_kP(0, p);

        m_driveLeftA.config_kI(0, i);
        m_driveRightA.config_kI(0, i);

        m_driveLeftA.config_kD(0, d);
        m_driveRightA.config_kD(0, d);

        m_driveLeftA.config_kF(0, f);
        m_driveRightA.config_kF(0, f);
    }

    public void setEncoderPositions(double left, double right) {
        m_driveLeftA.setSelectedSensorPosition(left);
        m_driveRightA.setSelectedSensorPosition(right);
    }

    public void setPositionGoal(double leftGoal, double rightGoal) {
        m_driveLeftA.set(ControlMode.Position, leftGoal);
        m_driveRightA.set(ControlMode.Position, rightGoal);
    }

    public void setVelocityGoal(double left, double right) {
        m_driveLeftA.set(ControlMode.Velocity, left);
        m_driveRightA.set(ControlMode.Velocity, right);
    }

    public double getClosedLoopError() {
        return m_driveLeftA.getClosedLoopError();
    }
}
