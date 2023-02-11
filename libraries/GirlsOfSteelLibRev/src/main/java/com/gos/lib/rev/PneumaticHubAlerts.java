package com.gos.lib.rev;

import edu.wpi.first.hal.REVPHFaults;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PowerDistribution;
import org.littletonrobotics.frc2023.util.Alert;

public class PneumaticHubAlerts {
    private static final String ALERT_NAME = "pneumatic hub";

    private final PneumaticHub m_pneumaticHub;
    private final Alert m_alert;

    public PneumaticHubAlerts(PneumaticHub pneumaticHub) {
        m_pneumaticHub = pneumaticHub;
        m_alert = new Alert("pneumatic hub", Alert.AlertType.ERROR);
    }

    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void checkAlerts() {
        // lookie so many if statements :)))))))
        StringBuilder alertMessageBuilder = new StringBuilder(400);
        alertMessageBuilder.append(ALERT_NAME);

        REVPHFaults faults = m_pneumaticHub.getFaults();
        if (faults.Channel0Fault) {
            alertMessageBuilder.append(" channel 0 error");
        }
        if (faults.Channel1Fault) {
            alertMessageBuilder.append(" channel 1 error");
        }
        if (faults.Channel2Fault) {
            alertMessageBuilder.append(" channel 2 error");
        }
        if (faults.Channel3Fault) {
            alertMessageBuilder.append(" channel 3 error");
        }
        if (faults.Channel4Fault) {
            alertMessageBuilder.append(" channel 4 error");
        }
        if (faults.Channel5Fault) {
            alertMessageBuilder.append(" channel 5 error");
        }
        if (faults.Channel6Fault) {
            alertMessageBuilder.append(" channel 6 error");
        }
        if (faults.Channel7Fault) {
            alertMessageBuilder.append(" channel 7 error");
        }
        if (faults.Channel8Fault) {
            alertMessageBuilder.append(" channel 8 error");
        }
        if (faults.Channel9Fault) {
            alertMessageBuilder.append(" channel 9 error");
        }
        if (faults.Channel10Fault) {
            alertMessageBuilder.append(" channel 10 error");
        }
        if (faults.Channel11Fault) {
            alertMessageBuilder.append(" channel 11 error");
        }
        if (faults.Channel12Fault) {
            alertMessageBuilder.append(" channel 12 error");
        }
        if (faults.Channel13Fault) {
            alertMessageBuilder.append(" channel 13 error");
        }
        if (faults.Channel14Fault) {
            alertMessageBuilder.append(" channel 14 error");
        }
        if (faults.Channel15Fault) {
            alertMessageBuilder.append(" channel 15 error");
        }
        if (faults.Channel6Fault) {
            alertMessageBuilder.append(" channel 16 error");
        }
        if (faults.Brownout) {
            alertMessageBuilder.append(" brownout");
        }
        if (faults.CompressorOverCurrent) {
            alertMessageBuilder.append(" compressor over current");
        }
        if (faults.CanWarning) {
            alertMessageBuilder.append(" can warning");
        }
        if (faults.SolenoidOverCurrent) {
            alertMessageBuilder.append(" solenoid over current");
        }
        if (faults.CompressorOpen) {
            alertMessageBuilder.append(" compressor open");
        }
        if (faults.HardwareFault) {
            alertMessageBuilder.append(" hardware fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }
}
