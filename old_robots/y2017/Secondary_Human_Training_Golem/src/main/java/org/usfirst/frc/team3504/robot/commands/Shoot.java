package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class Shoot extends Command {

    private static final int LOOP_TIMEOUT = 50; // ~1sec of time

    private int m_loopCounter; // increment each time execute runs
    private boolean m_isLowMotorRunning; // if we have started the low
    // motor yet
    private final int m_shooterSpeed;
    private final Shooter m_shooter;

    public Shoot(Shooter shooter, int speed) {
        m_shooter = shooter;
        requires(m_shooter);
        m_shooterSpeed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_loopCounter = 0;
        m_isLowMotorRunning = false;
        m_shooter.setShooterSpeed(m_shooterSpeed);
        SmartDashboard.putBoolean("Low Shooter Running", m_shooter.isLowShooterMotorRunning());
        System.out.println("Shoot Initialzed with " + m_shooterSpeed + " as speed");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putNumber("High Shooter Speed", m_shooter.getHighShooterSpeed());
        SmartDashboard.putNumber("Low Shooter Speed", m_shooter.getLowShooterSpeed());
        if (!m_isLowMotorRunning && (m_shooter.isHighShooterAtSpeed() || m_loopCounter > LOOP_TIMEOUT)) {
            System.out.println("LoopCounter timeout: " + m_loopCounter + "\t" + m_shooter.isHighShooterAtSpeed());
            m_shooter.startLowShooterMotor();
            m_isLowMotorRunning = true;
        }
        SmartDashboard.putBoolean("Low Shooter Running", m_shooter.isLowShooterMotorRunning());
        m_shooter.runHighShooterMotor();
        m_shooter.runLowShooterMotor();
        m_loopCounter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_shooter.stopLowShooterMotor();
        m_shooter.stopShooterMotors();
        System.out.println("Shoot Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
