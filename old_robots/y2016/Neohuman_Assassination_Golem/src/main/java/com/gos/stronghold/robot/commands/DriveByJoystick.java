package com.gos.stronghold.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByJoystick extends Command {

    private final Joystick m_drivingStickForward = new Joystick(0);
    private final Joystick m_drivingStickBackward = new Joystick(1);
    private final Chassis m_chassis;

    public DriveByJoystick(Chassis chassis) {
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
        if (m_chassis.getTeleDrivingDirection() == Chassis.TeleDriveDirection.FWD) {
            m_chassis.driveByJoystick(m_drivingStickForward.getY(), m_drivingStickForward.getX());
        } else if (m_chassis.getTeleDrivingDirection() == Chassis.TeleDriveDirection.REV) {
            m_chassis.driveByJoystick(-m_drivingStickForward.getY(), m_drivingStickBackward.getX());
        } else {
            throw new IllegalArgumentException();
        }
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
