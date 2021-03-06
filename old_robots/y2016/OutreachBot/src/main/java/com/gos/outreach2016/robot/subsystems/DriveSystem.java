package com.gos.outreach2016.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.gos.outreach2016.robot.RobotMap;

/**
 *
 */
public class DriveSystem extends SubsystemBase {
    private final WPI_TalonSRX m_driveLeftA;
    private final WPI_TalonSRX m_driveLeftB;
    private final WPI_TalonSRX m_driveLeftC;

    private final WPI_TalonSRX m_driveRightA;
    private final WPI_TalonSRX m_driveRightB;
    private final WPI_TalonSRX m_driveRightC;

    private final DifferentialDrive m_robotDrive;

    private double m_encOffsetValue;

    public DriveSystem() {
        m_driveLeftA = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_A_CAN_ID);
        //addChild("Drive Left A", driveLeftA);
        m_driveLeftB = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_B_CAN_ID);
        //addChild("Drive Left B", driveLeftB);
        m_driveLeftC = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_C_CAN_ID);
        //addChild("Drive Left C", driveLeftC);

        m_driveRightA = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_A_CAN_ID);
        //addChild("Drive Right A", driveRightA);
        m_driveRightB = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_B_CAN_ID);
        //addChild("Drive Right B", driveRightB);
        m_driveRightC = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_C_CAN_ID);
        //addChild("Drive Right C", driveRightC);

        // On each side, all three drive motors MUST run at the same speed.
        // Use the CAN Talon Follower mode to set the speed of B and C,
        // making always run at the same speed as A.
        m_driveLeftB.follow(m_driveLeftA);
        m_driveLeftC.follow(m_driveLeftA);
        m_driveRightB.follow(m_driveRightA);
        m_driveRightC.follow(m_driveRightA);

        // Define a robot drive object in terms of only the A motors.
        // The B and C motors will play along at the same speed (see above.)
        m_robotDrive = new DifferentialDrive(m_driveLeftA, m_driveRightA);

        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setMaxOutput(1.0);




    }



    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void takeJoystickInputs(Joystick joystk) {
        m_robotDrive.arcadeDrive(joystk.getY(), joystk.getX());
    }

    public void forward() {
        m_robotDrive.arcadeDrive(1.0, 0);
    }

    public void stop() {
        m_robotDrive.arcadeDrive(/* speed */0, /* curve */0);
    }

    public double getEncoderRight() {
        return m_driveRightA.getSelectedSensorPosition();
    }

    public double getEncoderLeft() {
        return m_driveLeftA.getSelectedSensorPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderLeft() - m_encOffsetValue) * RobotMap.DISTANCE_PER_PULSE;
    }

    public void resetDistance() {
        m_encOffsetValue = getEncoderLeft();
    }
}
