package org.usfirst.frc.team3504.robot.commands;



import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {

	private int loopCounter; //increment each time execute runs
	private boolean lowMotorSet = false;
	private final int LOOP_TIMEOUT = 50; //=1sec of time
	private int shooterSpeed;
	
    public Shoot(int speed) {
         requires(Robot.shooter);
         shooterSpeed = speed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	loopCounter = 0;
    	Robot.shooter.resetEncoders();
    	Robot.shooter.setShooterSpeed(shooterSpeed);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if ((!lowMotorSet) && (Robot.shooter.isHighShooterAtSpeed() || loopCounter > LOOP_TIMEOUT)){
    		Robot.shooter.startLowShooterMotor();
    		lowMotorSet = true;
    	}
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
