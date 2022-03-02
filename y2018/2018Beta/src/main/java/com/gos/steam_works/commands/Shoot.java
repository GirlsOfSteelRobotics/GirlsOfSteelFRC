package com.gos.steam_works.commands;

import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shoot extends CommandBase {

    private static final int LOOP_TIMEOUT = 50; // ~1sec of time

    private final Shooter m_shooter;
    private int m_loopCounter; // increment each time execute runs
    private boolean m_isLowMotorRunning; // if we have started the low
    // motor yet
    private final int m_shooterSpeed;

    public Shoot(Shooter shooter, int speed) {
        m_shooter = shooter;
        addRequirements(m_shooter);
        m_shooterSpeed = speed;
    }


    @Override
    public void initialize() {
        m_loopCounter = 0;
        m_isLowMotorRunning = false;
        m_shooter.setShooterSpeed(m_shooterSpeed);
        SmartDashboard.putBoolean("Low Shooter Running", m_shooter.isLowShooterMotorRunning());
        System.out.println("Shoot Initialized with " + m_shooterSpeed + " as speed");
    }


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


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_shooter.stopLowShooterMotor();
        m_shooter.stopShooterMotors();
        System.out.println("Shoot Finished");
    }


}
