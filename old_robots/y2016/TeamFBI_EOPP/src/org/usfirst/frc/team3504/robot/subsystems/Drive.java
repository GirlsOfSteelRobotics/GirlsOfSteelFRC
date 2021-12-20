
package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
    public static final double AUTO_DRIVE_SPEED = -0.1;

    private final WPI_TalonSRX m_driveSystemDriveLeft0;
    private final WPI_TalonSRX m_driveSystemDriveRight0;
    private final WPI_TalonSRX m_driveSystemDriveLeft1;
    private final WPI_TalonSRX m_driveSystemDriveRight1;
    private final WPI_TalonSRX m_driveSystemDriveLeft2;
    private final WPI_TalonSRX m_driveSystemDriveRight2;

    private final RobotDrive m_robotDrive;

    public Drive() {
        m_driveSystemDriveLeft0 = new WPI_TalonSRX(0);
        m_driveSystemDriveRight0 = new WPI_TalonSRX(1);
        m_driveSystemDriveLeft1 = new WPI_TalonSRX(2);
        m_driveSystemDriveRight1 = new WPI_TalonSRX(3);
        m_driveSystemDriveLeft2 = new WPI_TalonSRX(4);
        m_driveSystemDriveRight2 = new WPI_TalonSRX(5);

        // Follower: The m_motor will run at the same throttle as the specified
        // other talon.
        //set arguments refer to WPI_TalonSRX port numbers
        m_driveSystemDriveLeft1.follow(m_driveSystemDriveLeft0);
        m_driveSystemDriveRight1.follow(m_driveSystemDriveRight0);
        m_driveSystemDriveLeft2.follow(m_driveSystemDriveLeft0);
        m_driveSystemDriveRight2.follow(m_driveSystemDriveRight0);

        m_robotDrive = new RobotDrive(m_driveSystemDriveLeft0, m_driveSystemDriveRight0);

        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);
        //driveSystemRobotDrive2.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

    }

    @Override
    public void initDefaultCommand() {
    }

    public void moveByJoystick(Joystick joystick) {
        m_robotDrive.arcadeDrive(joystick);
    }

    public void driveAuto() {
        m_robotDrive.drive(AUTO_DRIVE_SPEED,0);
    }

    public void stop() {
        m_robotDrive.drive(0,0);
    }
}
