package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.OI.DriveStyle;
import org.usfirst.frc.team3504.robot.Robot;

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
    protected void initialize() {
    		Robot.oi.setDriveStyle();
    		Robot.chassis.setInverted(false);
    		System.out.println("Squared Units: " + Robot.oi.isSquaredOrQuickTurn());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		/*if (Robot.oi.getCurrentThrottle() > Robot.shifters.getShiftingThreshold()) {
    			Robot.shifters.shiftGear(Speed.kHigh);
    		} else if (Robot.oi.getCurrentThrottle() < Robot.shifters.getShiftingThreshold()) {
    			Robot.shifters.shiftGear(Speed.kLow);
    		}*/
    	
    		if (Robot.oi.getDriveStyle() == DriveStyle.joystickArcade) {
    			Robot.chassis.drive.curvatureDrive(Robot.oi.getJoystickOneUpAndDown(), Robot.oi.getJoystickOneRightAndLeft(), Robot.oi.isSquaredOrQuickTurn());
    		} else if (Robot.oi.getDriveStyle() == DriveStyle.gamePadArcade) {
        		Robot.chassis.drive.curvatureDrive(Robot.oi.getGamePadLeftUpAndDown(), Robot.oi.getGamePadLeftSideToSide(), Robot.oi.isSquaredOrQuickTurn());
    		} else if (Robot.oi.getDriveStyle() == DriveStyle.gamePadTank){
    			Robot.chassis.drive.tankDrive(Robot.oi.getGamePadRightUpAndDown(), Robot.oi.getGamePadRightSideToSide(), Robot.oi.isSquaredOrQuickTurn());
    		} else if (Robot.oi.getDriveStyle() == DriveStyle.joystickTank){
    			Robot.chassis.drive.tankDrive(Robot.oi.getGamePadRightUpAndDown(), Robot.oi.getGamePadRightSideToSide(), Robot.oi.isSquaredOrQuickTurn());
    		} 
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
