package com.gos.preseason2017.team2.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DifferentialDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.preseason2017.team2.robot.RobotMap;

/**
 *
 */
public class Chassis extends Subsystem {
    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;

    private final DifferentialDrive m_robotDrive;


    public Chassis() {
        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B);
        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B);

        m_driveLeftA.setNeutralMode(NeutralMode.Brake);
        m_driveLeftB.setNeutralMode(NeutralMode.Brake);
        m_driveRightA.setNeutralMode(NeutralMode.Brake);
        m_driveRightB.setNeutralMode(NeutralMode.Brake);

        m_driveLeftB.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);


        m_robotDrive = new DifferentialDrive(m_driveLeftA, m_driveRightA);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
    }

    public void driveByJoystick(Joystick joystick) {
        m_robotDrive.arcadeDrive(joystick);
    }

    public void driveSpeed(double speed) {
        m_robotDrive.arcadeDrive(-speed, 0);
    }

    public void stop() {
        // TODO Auto-generated method stub
        m_robotDrive.arcadeDrive(/* speed */0, /* curve */0);
    }

    public double getRightEncoderPosition() {
        return m_driveRightA.getSelectedSensorPosition();
    }

    public double getLeftEncoderPosition() {
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public void ahrsToSmartDashboard() {
        getEncoderDistance();
    }

    public void getEncoderDistance() { // NOPMD(LinguisticNaming)
        SmartDashboard.putNumber("Right Encoder", getRightEncoderPosition());
        SmartDashboard.putNumber("Left Encoder", getLeftEncoderPosition());
    }

}
