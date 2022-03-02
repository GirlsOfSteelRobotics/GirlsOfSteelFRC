package com.gos.steam_works.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.steam_works.robot.OI;
import com.gos.steam_works.robot.OI.DriveStyle;
import com.gos.steam_works.robot.subsystems.Chassis;

/**
 *
 */
public class Drive extends CommandBase {

    private final Chassis m_chassis;
    private final OI m_oi;

    public Drive(OI oi, Chassis chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_chassis = chassis;
        m_oi = oi;
        addRequirements(m_chassis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if (m_oi.getDriveStyle() == DriveStyle.oneStickArcade
            || m_oi.getDriveStyle() == DriveStyle.gamePadArcade) {
            m_chassis.arcadeDrive(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX());
            SmartDashboard.putNumber("Drive by Joystick Y: ", m_oi.getDrivingJoystickY());
            SmartDashboard.putNumber("Drive by Joystick X: ", m_oi.getDrivingJoystickX());
        } else {
            m_chassis.tankDrive(m_oi.getDrivingJoystickLeft(), m_oi.getDrivingJoystickRight());
            SmartDashboard.putNumber("Drive by Joystick Left:", m_oi.getDrivingJoystickLeft());
            SmartDashboard.putNumber("drive by Joystick Right:", m_oi.getDrivingJoystickRight());
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    public void stop() {
        m_chassis.stop();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        stop();
    }


}
