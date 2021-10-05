package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.OI;
import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.chassis);
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
        Robot.oi.setDriveStyle();
        System.out.println("Squared Units: " + Robot.oi.isSquared());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.oi.getDriveStyle() == OI.DriveStyle.joystickArcade || Robot.oi.getDriveStyle() == OI.DriveStyle.gamePadArcade) {
            Robot.chassis.drive.arcadeDrive(Robot.oi.getDrivingJoystickY(), Robot.oi.getDrivingJoystickX(), Robot.oi.isSquared());
        } else if (Robot.oi.getDriveStyle() == OI.DriveStyle.gamePadTank || Robot.oi.getDriveStyle() == OI.DriveStyle.joystickTank) {
            Robot.chassis.drive.tankDrive(Robot.oi.getDrivingJoystickY(), Robot.oi.getDrivingJoystickX(), Robot.oi.isSquared());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    public void stop() {
        Robot.chassis.stop();
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
