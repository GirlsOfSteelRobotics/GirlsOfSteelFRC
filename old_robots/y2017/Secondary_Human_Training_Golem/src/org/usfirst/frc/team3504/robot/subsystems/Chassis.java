package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class Chassis extends Subsystem {
    private final CANTalon m_driveLeftA;
    private final CANTalon m_driveLeftB;
    private final CANTalon m_driveLeftC;

    private final CANTalon m_driveRightA;
    private final CANTalon m_driveRightB;
    private final CANTalon m_driveRightC;

    private final RobotDrive m_robotDrive;

    public Chassis() {
        m_driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B);
        m_driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C);
        m_driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B);
        m_driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveLeftC.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);
        m_driveRightC.setNeutralMode(NeutralMode.Brake);

        m_driveLeftB.changeControlMode(ControlMode.Follower);
        m_driveLeftC.changeControlMode(ControlMode.Follower);
        m_driveRightB.changeControlMode(ControlMode.Follower);
        m_driveRightC.changeControlMode(ControlMode.Follower);
        m_driveLeftB.set(m_driveLeftA.getDeviceID());
        m_driveLeftC.set(m_driveLeftA.getDeviceID());
        m_driveRightB.set(m_driveRightA.getDeviceID());
        m_driveRightC.set(m_driveRightA.getDeviceID());

        setupEncoder(m_driveLeftA);
        setupEncoder(m_driveRightA);

        m_robotDrive = new RobotDrive(m_driveLeftA, m_driveRightA);
        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.2);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);

        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);

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

    public CANTalon getLeftTalon() {
        return m_driveLeftA;
    }

    public CANTalon getRightTalon() {
        return m_driveRightA;
    }

    public void arcadeDrive(double throttle, double turn) {
        m_robotDrive.arcadeDrive(throttle, turn);
    }

    public void tankDrive(double left, double right) {
        m_robotDrive.tankDrive(left, right);
    }

    public final void setupEncoder(CANTalon talon) { // only call this on non-follower
                                                // talons
        // Set Encoder Types
        talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        talon.configEncoderCodesPerRev((int) RobotMap.CODES_PER_WHEEL_REV);
        talon.reverseSensor(false);
    }

    public void setupFPID(CANTalon talon) { // values work with QuadEncoder for
                                            // drive talons
        // PID Values
        talon.setPosition(0);
        talon.config_kF(0, 0);
        talon.config_kP(0, 0.32); // 0.64 good
        talon.config_kI(0, 0.0);
        talon.config_kD(0, 0.0);
    }

    public void turn(double speed, double curve) {
        m_robotDrive.drive(speed, curve);
    }

    public void stop() {
        m_driveLeftA.set(0);
        m_driveRightA.set(0);
    }

    public void setPositionMode() {
        m_driveLeftA.changeControlMode(ControlMode.Position);
        m_driveRightA.changeControlMode(ControlMode.Position);
    }

    public void setPercentVbusMode() {
        m_driveLeftA.changeControlMode(ControlMode.PercentOutput);
        m_driveRightA.changeControlMode(ControlMode.PercentOutput);
    }

    public void setSpeedMode() {
        m_driveLeftA.changeControlMode(ControlMode.Velocity);
        m_driveRightA.changeControlMode(ControlMode.Velocity);
    }

    public void setMotionProfileMode() {
        m_driveLeftA.changeControlMode(ControlMode.MotionProfile);
        m_driveRightA.changeControlMode(ControlMode.MotionProfile);
    }

}
