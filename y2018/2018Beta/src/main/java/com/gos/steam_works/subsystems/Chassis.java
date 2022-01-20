package com.gos.steam_works.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public final class Chassis extends Subsystem {
    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;
    private final WPI_TalonSRX m_driveLeftC;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;
    private final WPI_TalonSRX m_driveRightC;

    private final DifferentialDrive m_drive;

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

        //drive.setSafetyEnabled(false);
        //drive.setExpiration(0.2);
        //drive.setMaxOutput(1.0);

        m_driveLeftA.setInverted(false);
        m_driveRightA.setInverted(false);

        m_drive = new DifferentialDrive(m_driveLeftA, m_driveRightA);
        //drive.setInvertedMotor(DifferentialDrive.MotorType.kRearLeft, false);
        //drive.setInvertedMotor(DifferentialDrive.MotorType.kRearRight, false);

        //      addChild("driveLeftA", driveLeftA);
        //      addChild("driveLeftB", driveLeftB);
        //      addChild("driveLeftC", driveLeftC);
        //      addChild("driveRightA", driveRightA);
        //      addChild("driveRightB", driveRightB);
        //      addChild("driveRightC", driveRightC);
    }

    @Override
    public void initDefaultCommand() {
    }

    // TODO(pj) Remove
    public WPI_TalonSRX getLeftTalon() {
        return m_driveLeftA;
    }

    public WPI_TalonSRX getRightTalon() {
        return m_driveRightA;
    }

    public void setupEncoder(WPI_TalonSRX talon) { // only call this on non-follower
        // talons
        // Set Encoder Types
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        //talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
        talon.setSensorPhase(true);
    }

    public void setupFPID(WPI_TalonSRX talon) { // values work with QuadEncoder for
        // drive talons
        // PID Values
        talon.setSelectedSensorPosition(0, 0, 0);
        talon.config_kF(0, 0, 0);
        talon.config_kP(0, 0.32, 0);
        talon.config_kI(0, 0, 0);
        talon.config_kD(0, 0, 0);
    }

    public void turn(double speed, double curve) {
        m_drive.curvatureDrive(speed, curve, false);
    }

    public void stop() {
        m_driveLeftA.set(ControlMode.PercentOutput, 0);
        m_driveRightA.set(ControlMode.PercentOutput, 0);
    }

    public void arcadeDrive(double speedX, double speedZ, boolean squared) {
        m_drive.arcadeDrive(speedX, speedZ, squared);
    }

    public void tankDrive(double left, double right, boolean squared) {
        m_drive.tankDrive(left, right, squared);
    }
}
