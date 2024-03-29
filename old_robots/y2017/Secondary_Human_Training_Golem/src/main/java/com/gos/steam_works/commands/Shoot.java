package com.gos.steam_works.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.steam_works.subsystems.Shooter;

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
        addRequirements(m_shooter);
        m_shooterSpeed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_loopCounter = 0;
        m_isLowMotorRunning = false;
        m_shooter.setShooterSpeed(m_shooterSpeed);
        SmartDashboard.putBoolean("Low Shooter Running", m_shooter.isLowShooterMotorRunning());
        System.out.println("Shoot Initialzed with " + m_shooterSpeed + " as speed");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
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
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_shooter.stopLowShooterMotor();
        m_shooter.stopShooterMotors();
        System.out.println("Shoot Finished");
    }


}
