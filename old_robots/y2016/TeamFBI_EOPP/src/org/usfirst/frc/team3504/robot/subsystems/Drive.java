
package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
    public static final double AUTO_DRIVE_SPEED = -0.1;

    private final CANTalon m_driveSystemDriveLeft0;
    private final CANTalon m_driveSystemDriveRight0;
    private final CANTalon m_driveSystemDriveLeft1;
    private final CANTalon m_driveSystemDriveRight1;
    private final CANTalon m_driveSystemDriveLeft2;
    private final CANTalon m_driveSystemDriveRight2;

    private final RobotDrive m_robotDrive;

    public Drive() {
        m_driveSystemDriveLeft0 = new CANTalon(0);
        m_driveSystemDriveRight0 = new CANTalon(1);
        m_driveSystemDriveLeft1 = new CANTalon(2);
        m_driveSystemDriveRight1 = new CANTalon(3);
        m_driveSystemDriveLeft2 = new CANTalon(4);
        m_driveSystemDriveRight2 = new CANTalon(5);

        // Follower: The m_motor will run at the same throttle as the specified
        // other talon.
        //set arguments refer to CANTalon port numbers
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
