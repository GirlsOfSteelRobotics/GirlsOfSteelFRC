package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final OI m_oi;
    private final Chassis m_chassis;

    public DriveByJoystick(OI oi, Chassis chassis) {
        m_oi = oi;
        m_chassis = chassis;
        requires(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        SmartDashboard.putBoolean("Drive by Joystick", true);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_chassis.driveByJoystick(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX());
        m_chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
        SmartDashboard.putBoolean("Drive by Joystick", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
