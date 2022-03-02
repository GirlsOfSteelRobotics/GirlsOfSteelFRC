package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.OI;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends CommandBase {

    private final OI m_oi;
    private final Chassis m_chassis;

    public DriveByJoystick(OI oi, Chassis chassis) {
        m_oi = oi;
        m_chassis = chassis;
        addRequirements(m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Drive by Joystick", true);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_chassis.driveByJoystick(m_oi.getDrivingJoystickY(), m_oi.getDrivingJoystickX());
        m_chassis.printEncoderValues();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_chassis.stop();
        SmartDashboard.putBoolean("Drive by Joystick", false);
    }


}
