package com.gos.steam_works.commands;

import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shoot extends Command {

    private int loopCounter; // increment each time execute runs
    private boolean isLowMotorRunning; // if we have started the low
    // motor yet
    private static final int LOOP_TIMEOUT = 50; // ~1sec of time
    private final int shooterSpeed;

    public Shoot(int speed) {
        requires(Robot.shooter);
        shooterSpeed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        loopCounter = 0;
        isLowMotorRunning = false;
        Robot.shooter.setShooterSpeed(shooterSpeed);
        SmartDashboard.putBoolean("Low Shooter Running", Robot.shooter.isLowShooterMotorRunning());
        System.out.println("Shoot Initialzed with " + shooterSpeed + " as speed");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putNumber("High Shooter Speed", Robot.shooter.getHighShooterSpeed());
        SmartDashboard.putNumber("Low Shooter Speed", Robot.shooter.getLowShooterSpeed());
        if (!isLowMotorRunning && (Robot.shooter.isHighShooterAtSpeed() || loopCounter > LOOP_TIMEOUT)) {
            System.out.println("LoopCounter timeout: " + loopCounter + "\t" + Robot.shooter.isHighShooterAtSpeed());
            Robot.shooter.startLowShooterMotor();
            isLowMotorRunning = true;
        }
        SmartDashboard.putBoolean("Low Shooter Running", Robot.shooter.isLowShooterMotorRunning());
        Robot.shooter.runHighShooterMotor();
        Robot.shooter.runLowShooterMotor();
        loopCounter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.shooter.stopLowShooterMotor();
        Robot.shooter.stopShooterMotors();
        System.out.println("Shoot Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
