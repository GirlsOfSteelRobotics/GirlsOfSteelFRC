package com.gos.crescendo2024.subsystems;

import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private static final GosDoubleProperty SHOOTER_SPEED = new GosDoubleProperty(false, "ShooterSpeed", 0.5);
    private final SimableCANSparkMax m_shooterMotor;
    private final SparkMaxAlerts m_shooterMotorErrorAlerts;
    private final RelativeEncoder m_shooterEncoder;
    private final LoggingUtil m_networkTableEntries;


    public ShooterSubsystem() {
        m_shooterMotor = new SimableCANSparkMax(8, CANSparkLowLevel.MotorType.kBrushless);
        m_shooterMotor.restoreFactoryDefaults();
        m_shooterMotor.setInverted(true);
        m_shooterEncoder = m_shooterMotor.getEncoder();

        m_shooterMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        m_shooterMotor.setSmartCurrentLimit(10);
        m_shooterMotor.burnFlash();
        m_shooterMotorErrorAlerts = new SparkMaxAlerts(m_shooterMotor, "shooter motor");

        m_networkTableEntries = new LoggingUtil("Claw Subsystem");
        m_networkTableEntries.addDouble("Current Amps", m_shooterMotor::getOutputCurrent);
        m_networkTableEntries.addDouble("Output", m_shooterMotor::getAppliedOutput);
        m_networkTableEntries.addDouble("Velocity (RPM)", m_shooterEncoder::getVelocity);
    }

    @Override
    public void periodic() {
        m_shooterMotorErrorAlerts.checkAlerts();
        m_networkTableEntries.updateLogs();
    }

    public void tuneShootPercentage() {
        m_shooterMotor.set(SHOOTER_SPEED.getValue());
    }

    public void stopShooter() {
        m_shooterMotor.set(0);
    }

    // Command Factories

    public Command createTunePercentShootCommand() {
        return this.runEnd(this::tuneShootPercentage, this::stopShooter).withName("TuneShooterPercentage");
    }

}
