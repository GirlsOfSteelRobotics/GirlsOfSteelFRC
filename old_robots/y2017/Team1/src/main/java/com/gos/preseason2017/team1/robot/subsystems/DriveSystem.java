package com.gos.preseason2017.team1.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.preseason2017.team1.robot.RobotMap;

/**
 *
 */
public class DriveSystem extends Subsystem {

    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;
    private final WPI_TalonSRX m_driveLeftC;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;
    private final WPI_TalonSRX m_driveRightC;

    private final DifferentialDrive m_robotDrive;

    private double m_encOffsetValueRight;
    private double m_encOffsetValueLeft;

    private final Shifters m_shifters;

    public DriveSystem(Shifters shifters) {
        m_shifters = shifters;
        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A_CAN_ID);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B_CAN_ID);
        m_driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C_CAN_ID);
        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A_CAN_ID);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B_CAN_ID);
        m_driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C_CAN_ID);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveLeftC.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);
        m_driveRightC.setNeutralMode(NeutralMode.Brake);

        m_robotDrive = new DifferentialDrive(m_driveLeftA, m_driveRightA);

        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);

        m_driveLeftB.follow(m_driveLeftA);
        m_driveLeftC.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);
        m_driveRightC.follow(m_driveRightA);
    }

    @Override
    public void initDefaultCommand() {
    }

    public void takeJoystickInputs(Joystick joystk) {
        m_robotDrive.arcadeDrive(joystk);
    }

    public void driveByJoystick(double throttle, double steer) {
        SmartDashboard.putString("driveByJoystick?", throttle + "," + steer);
        m_robotDrive.arcadeDrive(throttle, steer);
    }

    public void drive(double moveValue, double rotateValue) {
        m_robotDrive.arcadeDrive(moveValue, rotateValue);
    }

    public void driveSpeed(double speed) {
        m_robotDrive.drive(-speed, 0);
    }

    public void stop() {
        m_robotDrive.drive(0, 0);
    }


    public void printEncoderValues() {
        getEncoderDistance();
    }

    public double getEncoderRight() {
        return -m_driveRightA.getSelectedSensorPosition();
    }

    public double getEncoderLeft() {
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public double getEncoderDistance() {
        if (m_shifters.getGearSpeed()) {
            SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - m_encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR);
            return (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_HIGH_GEAR;
        } else {
            SmartDashboard.putNumber("Chassis Encoders Right", (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
            SmartDashboard.putNumber("Chassis Encoders Left", (getEncoderLeft() - m_encOffsetValueLeft) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR);
            return (getEncoderRight() - m_encOffsetValueRight) * RobotMap.DISTANCE_PER_PULSE_LOW_GEAR;
        }
    }

    public void resetEncoderDistance() {
        m_encOffsetValueRight = getEncoderRight();
        m_encOffsetValueLeft = getEncoderLeft();
        getEncoderDistance();
    }
}
