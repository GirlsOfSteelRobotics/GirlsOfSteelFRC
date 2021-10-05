package com.gos.deep_space.subsystems;


import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.deep_space.RobotMap;
import com.gos.deep_space.commands.DriveByJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {

    private final WPI_TalonSRX m_masterLeft;
    private final WPI_TalonSRX m_followerLeft;

    private final WPI_TalonSRX m_masterRight;
    private final WPI_TalonSRX m_followerRight;

    private final DifferentialDrive m_drive;

    public Chassis() {
        m_masterLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_MASTER_TALON);
        m_followerLeft = new WPI_TalonSRX(RobotMap.DRIVE_LEFT_FOLLOWER_TALON);

        m_masterRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_MASTER_TALON);
        m_followerRight = new WPI_TalonSRX(RobotMap.DRIVE_RIGHT_FOLLOWER_TALON);

        m_masterLeft.setNeutralMode(NeutralMode.Brake);
        m_followerLeft.setNeutralMode(NeutralMode.Brake);

        m_masterRight.setNeutralMode(NeutralMode.Brake);
        m_followerRight.setNeutralMode(NeutralMode.Brake);

        // inverted should be true for Laika
        // masterLeft.setInverted(true);
        // followerLeft.setInverted(true);

        // masterRight.setInverted(true);
        // followerRight.setInverted(true);

        m_followerLeft.follow(m_masterLeft, FollowerType.PercentOutput);
        m_followerRight.follow(m_masterRight, FollowerType.PercentOutput);

        m_drive = new DifferentialDrive(m_masterLeft, m_masterRight);
        m_drive.setSafetyEnabled(true);
        m_drive.setExpiration(0.1);
        m_drive.setMaxOutput(0.8);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveByJoystick());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public WPI_TalonSRX getLeftTalon() {
        return m_masterLeft;
    }

    public WPI_TalonSRX getRightTalon() {
        return m_masterRight;
    }

    public void driveByJoystick(double dirY, double dirX) {
        SmartDashboard.putString("driveByJoystick?", dirY + "," + dirX);
        double forward = dirY * Math.abs(dirY);
        m_drive.arcadeDrive(forward, dirX);
    }

    public void setSpeed(double speed) {
        m_drive.arcadeDrive(speed, 0);
    }

    public void stop() {
        m_drive.stopMotor();
    }
}
