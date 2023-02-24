package com.gos.lib.ctre;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.Pigeon2_Faults;
import org.littletonrobotics.frc2023.util.Alert;

public class PigeonAlerts {
    private static final String ALERT_NAME = "pigeon";
    private static final String STICKY_ALERT_NAME = "pigeon (sticky)";

    private final Pigeon2 m_pigeon;
    private final Alert m_alert;
    private final Alert m_alertSticky;


    public PigeonAlerts(Pigeon2 pigeon2) {
        m_pigeon = pigeon2;
        m_alert = new Alert("pigeon", Alert.AlertType.ERROR);
        m_alertSticky = new Alert("pigeon (sticky)", Alert.AlertType.ERROR);
    }

    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void checkFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(ALERT_NAME);

        Pigeon2_Faults faults = new Pigeon2_Faults();
        m_pigeon.getFaults(faults);

        if (faults.HardwareFault) {
            alertMessageBuilder.append(" Hardwar Fault");
        }
        if (faults.APIError) {
            alertMessageBuilder.append(" API Error");
        }
        if (faults.UnderVoltage) {
            alertMessageBuilder.append(" Under Voltage fault");
        }
        if (faults.ResetDuringEn) {
            alertMessageBuilder.append(" Reset During En fault");
        }
        if (faults.SaturatedRotVelocity) {
            alertMessageBuilder.append(" Saturated Rot Velocity fault");
        }
        if (faults.SaturatedAccel) {
            alertMessageBuilder.append(" Saturated Accel Fault");
        }
        if (faults.SaturatedMag) {
            alertMessageBuilder.append(" Saturated Mag fault");
        }
        if (faults.BootIntoMotion) {
            alertMessageBuilder.append(" Boot into motion fault");
        }
        if (faults.MagnetometerFault) {
            alertMessageBuilder.append(" Magnetometer fault");
        }
        if (faults.GyroFault) {
            alertMessageBuilder.append(" Gyro fault");
        }
        if (faults.AccelFault) {
            alertMessageBuilder.append(" Alert message builder fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void checkStickyFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(STICKY_ALERT_NAME);

        Pigeon2_Faults stickyFaults = new Pigeon2_Faults();
        m_pigeon.getStickyFaults(stickyFaults);

        if (stickyFaults.HardwareFault) {
            alertMessageBuilder.append(" Hardware Fault");
        }
        if (stickyFaults.APIError) {
            alertMessageBuilder.append(" APIE Error");
        }
        if (stickyFaults.UnderVoltage) {
            alertMessageBuilder.append(" Under Voltage fault");
        }
        if (stickyFaults.ResetDuringEn) {
            alertMessageBuilder.append(" Reset During En Fault");
        }
        if (stickyFaults.SaturatedRotVelocity) {
            alertMessageBuilder.append(" Saturated Rot Velocity fault");
        }
        if (stickyFaults.SaturatedAccel) {
            alertMessageBuilder.append(" Saturated Accel fault");
        }
        if (stickyFaults.SaturatedMag) {
            alertMessageBuilder.append(" Saturated Mag fault");
        }
        if (stickyFaults.BootIntoMotion) {
            alertMessageBuilder.append(" Boot into motion fault");
        }
        if (stickyFaults.MagnetometerFault) {
            alertMessageBuilder.append(" Magnetometer Fault");
        }
        if (stickyFaults.GyroFault) {
            alertMessageBuilder.append(" Gyro fault");
        }
        if (stickyFaults.AccelFault) {
            alertMessageBuilder.append(" Accel Fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));
    }
}
