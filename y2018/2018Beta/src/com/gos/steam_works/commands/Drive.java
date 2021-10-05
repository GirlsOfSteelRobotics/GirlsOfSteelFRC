package com.gos.steam_works.commands;

import com.gos.steam_works.OI;
import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {

    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        // Change mode to Percent Vbus
        // V per sec; 12 = zero to full speed in 1 second
        //leftTalon.configVoltageCompSaturation(24.0, 0);
        //rightTalon.configVoltageCompSaturation(24.0, 0);
        //leftTalon.setVoltageRampRate(24.0);  IS THIS THE SAME AS ABOVE???
        //rightTalon.setVoltageRampRate(24.0);
        Robot.m_oi.setDriveStyle();
        System.out.println("Squared Units: " + Robot.m_oi.isSquared());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.m_oi.getDriveStyle() == OI.DriveStyle.joystickArcade || Robot.m_oi.getDriveStyle() == OI.DriveStyle.gamePadArcade) {
            Robot.m_chassis.m_drive.arcadeDrive(Robot.m_oi.getDrivingJoystickY(), Robot.m_oi.getDrivingJoystickX(), Robot.m_oi.isSquared());
        } else if (Robot.m_oi.getDriveStyle() == OI.DriveStyle.gamePadTank || Robot.m_oi.getDriveStyle() == OI.DriveStyle.joystickTank) {
            Robot.m_chassis.m_drive.tankDrive(Robot.m_oi.getDrivingJoystickY(), Robot.m_oi.getDrivingJoystickX(), Robot.m_oi.isSquared());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    public void stop() {
        Robot.m_chassis.stop();
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
