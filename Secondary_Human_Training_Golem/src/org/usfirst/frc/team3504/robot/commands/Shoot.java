package org.usfirst.frc.team3504.robot.commands;



import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shoot extends Command {

	private int loopCounter; //increment each time execute runs
	private boolean lowMotorSet = false; //if we have started the low motor yet
	private final int LOOP_TIMEOUT = 50; //~1sec of time
	private int shooterSpeed;
	
    public Shoot(int speed) {
         requires(Robot.shooter);
         shooterSpeed = speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	loopCounter = 0;
    	lowMotorSet = true;
    	Robot.shooter.setShooterSpeed(shooterSpeed);
    	SmartDashboard.putBoolean("Low Shooter Running", Robot.shooter.isLowShooterMotorRunning());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("High Shooter Speed", Robot.shooter.getHighShooterSpeed());
    	SmartDashboard.putNumber("Low Shooter Speed", Robot.shooter.getLowShooterSpeed());
    	if (((!lowMotorSet) && (Robot.shooter.isHighShooterAtSpeed()) || loopCounter > LOOP_TIMEOUT)){
    		Robot.shooter.startLowShooterMotor();
    		lowMotorSet = true;
    	}
    	SmartDashboard.putBoolean("Low Shooter Running", Robot.shooter.isLowShooterMotorRunning());
    	Robot.shooter.runHighShooterMotor();
    	Robot.shooter.runLowShooterMotor();
    	loopCounter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stopLowShooterMotor();
    	Robot.shooter.stopShooterMotors();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
