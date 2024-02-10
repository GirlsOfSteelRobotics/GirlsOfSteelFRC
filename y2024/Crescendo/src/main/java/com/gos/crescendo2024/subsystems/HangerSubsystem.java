package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangerSubsystem extends SubsystemBase {
    private final SimableCANSparkMax m_hangerMotorPrimary;
    private final SimableCANSparkMax m_hangerMotorSecondary;
    private final SparkMaxAlerts m_hangerMotorErrorAlerts;
    private final RelativeEncoder m_hangerPrimaryEncoder;
    private final RelativeEncoder m_hangerSecondaryEncoder;
    private static final GosDoubleProperty HANGER_DOWN_SPEED = new GosDoubleProperty(true, "Hanger_Down_Speed", -1);
    private static final GosDoubleProperty HANGER_UP_SPEED = new GosDoubleProperty(true, "Hanger_Up_Speed", 1);

    public HangerSubsystem() {
        m_hangerMotorPrimary = new SimableCANSparkMax(Constants.HANGER_MOTOR_PRIMARY, CANSparkLowLevel.MotorType.kBrushless);
        m_hangerMotorPrimary.restoreFactoryDefaults();
        m_hangerMotorPrimary.setInverted(false);
        m_hangerPrimaryEncoder = m_hangerMotorPrimary.getEncoder();
        m_hangerMotorPrimary.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_hangerMotorPrimary.setSmartCurrentLimit(60);
        m_hangerMotorPrimary.burnFlash();
        m_hangerMotorErrorAlerts = new SparkMaxAlerts(m_hangerMotorPrimary, "hanger motor");

        m_hangerMotorSecondary = new SimableCANSparkMax(Constants.HANGER_MOTOR_SECONDARY, CANSparkLowLevel.MotorType.kBrushless);
        m_hangerMotorSecondary.restoreFactoryDefaults();
        m_hangerMotorSecondary.setInverted(false);
        m_hangerSecondaryEncoder = m_hangerMotorSecondary.getEncoder();
        m_hangerMotorSecondary.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_hangerMotorSecondary.setSmartCurrentLimit(60);
        m_hangerMotorSecondary.burnFlash();

    }

    @Override
    public void periodic() {
        m_hangerMotorErrorAlerts.checkAlerts();
    }

    public double getPrimaryHangerSpeed() {
        return m_hangerMotorPrimary.getAppliedOutput();
    }

    public double getSecondaryHangerSpeed() {
        return m_hangerMotorSecondary.getAppliedOutput();
    }

    public double getPrimaryHangerHeight() {
        return m_hangerPrimaryEncoder.getPosition();
    }

    public double getSecondaryHangerHeight() {
        return m_hangerSecondaryEncoder.getPosition();
    }

    public void setPrimaryHangerSpeed(double speed) {
        m_hangerMotorPrimary.set(speed);
    }

    public void setSecondaryHangerSpeed(double speed) {
        m_hangerMotorSecondary.set(speed);
    }

    public void runHangerUp() {
        setPrimaryHangerSpeed(HANGER_UP_SPEED.getValue());
        setSecondaryHangerSpeed(HANGER_UP_SPEED.getValue());
    }

    public void runHangerDown() {
        setPrimaryHangerSpeed(HANGER_DOWN_SPEED.getValue());
        setSecondaryHangerSpeed(HANGER_DOWN_SPEED.getValue());
    }

    public void stopHanger() {
        stopPrimaryMotor();
        stopSecondaryMotor();
    }

    public void stopPrimaryMotor() {
        m_hangerMotorPrimary.set(0);
    }

    public void stopSecondaryMotor() {
        m_hangerMotorSecondary.set(0);
    }

    /////////////////////////////////////
    // Command Factories
    /////////////////////////////////////
    public Command createHangerUp() {
        return this.run(this::runHangerUp);
    }

    public Command createHangerDown() {
        return this.run(this::runHangerDown);
    }

    public Command createHangerStop() {
        return this.run(this::stopHanger);
    }
}

