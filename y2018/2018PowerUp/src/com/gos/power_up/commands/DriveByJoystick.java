package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByJoystick extends Command {

    public DriveByJoystick() {
        requires(Robot.chassis);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.oi.setDriveStyle();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        boolean highGear = true;

//		if (Robot.shifters.getGearSpeed().equals("kHigh")){
//			highGear = true;
//		}
        double throttleFactor;


        //throttle runs .225 speed
        //speedy is 100%
        //regular is 90% speed

        if (highGear) {
            if (Robot.oi.isThrottle()) {
                Robot.chassis.getLeftTalon().configOpenloopRamp(0.37, 10); //blinky numbers
                Robot.chassis.getRightTalon().configOpenloopRamp(0.37, 10);
                throttleFactor = .2;
            } else if (Robot.oi.isSpeedy()) {
                Robot.chassis.getLeftTalon().configOpenloopRamp(0.7, 10); //blinky numbers
                Robot.chassis.getRightTalon().configOpenloopRamp(0.7, 10);
                throttleFactor = 1;
            } else {
                throttleFactor = .65;
            }
        } else {
            if (Robot.oi.isThrottle()) {
                throttleFactor = .225;
            } else if (Robot.oi.isSpeedy()) {
                throttleFactor = 1;
            } else {
                throttleFactor = .9;
            }
        }


        Robot.chassis.drive.curvatureDrive(Robot.oi.getAmazonLeftUpAndDown() * throttleFactor,
            Robot.oi.getAmazonRightSideToSide() * throttleFactor, true);


    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
