package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class Drive extends Command {

    private final Chassis m_chassis;
    private final OI m_oi;

    public Drive(OI oi, Chassis chassis) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_chassis = chassis;
        m_oi = oi;
        requires(m_chassis);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_oi.getDriveStyle() == DriveStyle.oneStickArcade
                || m_oi.getDriveStyle() == DriveStyle.gamePadArcade) {
            m_chassis.arcadeDrive(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX());
            SmartDashboard.putNumber("Drive by Joystick Y: ", m_oi.getDrivingJoystickY());
            SmartDashboard.putNumber("Drive by Joystick X: ", m_oi.getDrivingJoystickX());
        }
        else {
            m_chassis.tankDrive(m_oi.getDrivingJoystickLeft(), m_oi.getDrivingJoystickRight());
            SmartDashboard.putNumber("Drive by Joystick Left:", m_oi.getDrivingJoystickLeft());
            SmartDashboard.putNumber("drive by Joystick Right:", m_oi.getDrivingJoystickRight());
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    public void stop() {
        m_chassis.stop();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
