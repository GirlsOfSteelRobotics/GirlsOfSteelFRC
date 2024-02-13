package com.gos.crescendo2024.subsystems;

import com.gos.crescendo2024.Constants;
import com.gos.lib.logging.LoggingUtil;
import com.gos.lib.properties.GosDoubleProperty;
import com.gos.lib.rev.alerts.SparkMaxAlerts;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangerSubsystem extends SubsystemBase {
    private static final GosDoubleProperty HANGER_DOWN_SPEED = new GosDoubleProperty(true, "Hanger_Down_Speed", -1);
    private static final GosDoubleProperty HANGER_UP_SPEED = new GosDoubleProperty(true, "Hanger_Up_Speed", 1);

    private final SimableCANSparkMax m_hangerMotorPrimary;
    private final RelativeEncoder m_hangerPrimaryEncoder;
    private final SparkMaxAlerts m_hangerPrimaryMotorErrorAlerts;

    private final SimableCANSparkMax m_hangerMotorSecondary;
    private final RelativeEncoder m_hangerSecondaryEncoder;
    private final SparkMaxAlerts m_hangerSecondaryMotorErrorAlerts;

    private final LoggingUtil m_networkTableEntries;

    public HangerSubsystem() {
        m_hangerMotorPrimary = new SimableCANSparkMax(Constants.HANGER_MOTOR_PRIMARY, CANSparkLowLevel.MotorType.kBrushless);
        m_hangerMotorPrimary.restoreFactoryDefaults();
        m_hangerMotorPrimary.setInverted(false);
        m_hangerPrimaryEncoder = m_hangerMotorPrimary.getEncoder();
        m_hangerMotorPrimary.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_hangerMotorPrimary.setSmartCurrentLimit(60);
        m_hangerMotorPrimary.burnFlash();
        m_hangerPrimaryMotorErrorAlerts = new SparkMaxAlerts(m_hangerMotorPrimary, "hanger a");

        m_hangerMotorSecondary = new SimableCANSparkMax(Constants.HANGER_MOTOR_SECONDARY, CANSparkLowLevel.MotorType.kBrushless);
        m_hangerMotorSecondary.restoreFactoryDefaults();
        m_hangerMotorSecondary.setInverted(false);
        m_hangerSecondaryEncoder = m_hangerMotorSecondary.getEncoder();
        m_hangerMotorSecondary.setIdleMode(CANSparkMax.IdleMode.kCoast);
        m_hangerMotorSecondary.setSmartCurrentLimit(60);
        m_hangerMotorSecondary.burnFlash();
        m_hangerSecondaryMotorErrorAlerts = new SparkMaxAlerts(m_hangerMotorSecondary, "hanger b");

        m_networkTableEntries = new LoggingUtil("Hanger Subsystem");
        m_networkTableEntries.addDouble("Primary Hanger Vel: ", this::getPrimaryHangerSpeed);
        m_networkTableEntries.addDouble("Secondary Hanger Vel", this::getSecondaryHangerSpeed);
        m_networkTableEntries.addDouble("Primary Hanger Pos: ", m_hangerPrimaryEncoder::getPosition);
        m_networkTableEntries.addDouble("Secondary Hanger Pos", m_hangerSecondaryEncoder::getPosition);

    }

    @Override
    public void periodic() {
        m_hangerPrimaryMotorErrorAlerts.checkAlerts();
        m_hangerSecondaryMotorErrorAlerts.checkAlerts();
        m_networkTableEntries.updateLogs();
    }

    public void clearStickyFaults() {
        m_hangerMotorPrimary.clearFaults();
        m_hangerMotorSecondary.clearFaults();
    }

    public double getPrimaryHangerSpeed() {
        return m_hangerMotorPrimary.getAppliedOutput();
    }

    public double getSecondaryHangerSpeed() {
        return m_hangerMotorSecondary.getAppliedOutput();
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
        return this.runEnd(this::runHangerUp, this::stopHanger);
    }

    public Command createHangerDown() {
        return this.runEnd(this::runHangerDown, this::stopHanger);
    }
}

