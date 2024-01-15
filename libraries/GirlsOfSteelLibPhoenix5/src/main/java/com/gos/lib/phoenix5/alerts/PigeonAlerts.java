package com.gos.lib.phoenix5.alerts;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.Pigeon2_Faults;
import org.littletonrobotics.frc2023.util.Alert;

@SuppressWarnings("removal")
public class PigeonAlerts {
    private static final String ALERT_NAME = "pigeon";
    private static final String STICKY_ALERT_NAME = "pigeon (sticky)";

    private final Pigeon2 m_pigeon;
    private final Alert m_alert;
    private final Alert m_alertSticky;


    public PigeonAlerts(Pigeon2 pigeon2) {
        m_pigeon = pigeon2;
        m_alert = new Alert(ALERT_NAME, Alert.AlertType.ERROR);
        m_alertSticky = new Alert(STICKY_ALERT_NAME, Alert.AlertType.WARNING);
    }

    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    private void checkStickyFaults() {
        Pigeon2_Faults stickyFaults = new Pigeon2_Faults();
        m_pigeon.getStickyFaults(stickyFaults);
        checkFaults(stickyFaults, m_alertSticky, STICKY_ALERT_NAME);
    }

    private void checkFaults() {
        Pigeon2_Faults faults = new Pigeon2_Faults();
        m_pigeon.getFaults(faults);
        checkFaults(faults, m_alert, ALERT_NAME);
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void checkFaults(Pigeon2_Faults faults, Alert alert, String alertName) {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(alertName);

        if (faults.HardwareFault) {
            alertMessageBuilder.append("\nHardware Fault");
        }
        if (faults.APIError) {
            alertMessageBuilder.append("\nAPI Error");
        }
        if (faults.UnderVoltage) {
            alertMessageBuilder.append("\nUnder Voltage fault");
        }
        if (faults.ResetDuringEn) {
            alertMessageBuilder.append("\nReset During En fault");
        }
        if (faults.SaturatedRotVelocity) {
            alertMessageBuilder.append("\nSaturated Rot Velocity fault");
        }
        if (faults.SaturatedAccel) {
            alertMessageBuilder.append("\nSaturated Accel Fault");
        }
        if (faults.SaturatedMag) {
            alertMessageBuilder.append("\nSaturated Mag fault");
        }
        if (faults.BootIntoMotion) {
            alertMessageBuilder.append("\nBoot into motion fault");
        }
        if (faults.MagnetometerFault) {
            alertMessageBuilder.append("\nMagnetometer fault");
        }
        if (faults.GyroFault) {
            alertMessageBuilder.append("\nGyro fault");
        }
        if (faults.AccelFault) {
            alertMessageBuilder.append("\nAlert message builder fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        alert.setText(alertMessage);

        alert.set(!alertName.equals(alertMessage));
    }

}
