
package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.RobotMap;
import org.usfirst.frc.team3504.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
    RobotDrive robotDrive = RobotMap.driveRobotDrive;
    public static final double AUTO_DRIVE_SPEED = -0.1;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveCommand());
    }

    public void moveByJoystick(Joystick joystick) {
        robotDrive.arcadeDrive(joystick);
    }

    public void driveAuto() {
        robotDrive.drive(AUTO_DRIVE_SPEED,0);
    }

    public void stop() {
        robotDrive.drive(0,0);
    }
}
