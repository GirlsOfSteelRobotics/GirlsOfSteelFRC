package com.gos.lib.alerts;

import edu.wpi.first.hal.REVPHFaults;
import edu.wpi.first.hal.REVPHStickyFaults;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.PneumaticHub;

public class PneumaticHubAlerts {
    private static final String ALERT_NAME = "pneumatic hub";
    private static final String STICKY_ALERT_NAME = "pneumatic hub (sticky) ";

    private final PneumaticHub m_pneumaticHub;
    private final Alert m_alert;
    private final Alert m_alertSticky;

    public PneumaticHubAlerts(PneumaticHub pneumaticHub) {
        m_pneumaticHub = pneumaticHub;
        m_alert = new Alert("pneumatic hub", AlertType.kError);
        m_alertSticky = new Alert("pneumatic hub (sticky) ", AlertType.kWarning);
    }

    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void checkFaults() {
        // lookie so many if statements :)))))))
        StringBuilder alertMessageBuilder = new StringBuilder(400);
        alertMessageBuilder.append(ALERT_NAME);

        REVPHFaults faults = m_pneumaticHub.getFaults();
        if (faults.Channel0Fault) {
            alertMessageBuilder.append("\nchannel 0 error");
        }
        if (faults.Channel1Fault) {
            alertMessageBuilder.append("\nchannel 1 error");
        }
        if (faults.Channel2Fault) {
            alertMessageBuilder.append("\nchannel 2 error");
        }
        if (faults.Channel3Fault) {
            alertMessageBuilder.append("\nchannel 3 error");
        }
        if (faults.Channel4Fault) {
            alertMessageBuilder.append("\nchannel 4 error");
        }
        if (faults.Channel5Fault) {
            alertMessageBuilder.append("\nchannel 5 error");
        }
        if (faults.Channel6Fault) {
            alertMessageBuilder.append("\nchannel 6 error");
        }
        if (faults.Channel7Fault) {
            alertMessageBuilder.append("\nchannel 7 error");
        }
        if (faults.Channel8Fault) {
            alertMessageBuilder.append("\nchannel 8 error");
        }
        if (faults.Channel9Fault) {
            alertMessageBuilder.append("\nchannel 9 error");
        }
        if (faults.Channel10Fault) {
            alertMessageBuilder.append("\nchannel 10 error");
        }
        if (faults.Channel11Fault) {
            alertMessageBuilder.append("\nchannel 11 error");
        }
        if (faults.Channel12Fault) {
            alertMessageBuilder.append("\nchannel 12 error");
        }
        if (faults.Channel13Fault) {
            alertMessageBuilder.append("\nchannel 13 error");
        }
        if (faults.Channel14Fault) {
            alertMessageBuilder.append("\nchannel 14 error");
        }
        if (faults.Channel15Fault) {
            alertMessageBuilder.append("\nchannel 15 error");
        }
        if (faults.Channel6Fault) {
            alertMessageBuilder.append("\nchannel 16 error");
        }
        if (faults.Brownout) {
            alertMessageBuilder.append("\nbrownout");
        }
        if (faults.CompressorOverCurrent) {
            alertMessageBuilder.append("\ncompressor over current");
        }
        if (faults.CanWarning) {
            alertMessageBuilder.append("\ncan warning");
        }
        if (faults.SolenoidOverCurrent) {
            alertMessageBuilder.append("\nsolenoid over current");
        }
        if (faults.CompressorOpen) {
            alertMessageBuilder.append("\ncompressor open");
        }
        if (faults.HardwareFault) {
            alertMessageBuilder.append("\nhardware fault");
        }

        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }

    private void checkStickyFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(STICKY_ALERT_NAME);

        REVPHStickyFaults revphStickyFaults = m_pneumaticHub.getStickyFaults();

        if (revphStickyFaults.CompressorOverCurrent) {
            alertMessageBuilder.append("\nCompressor Over Current fault");
        }
        if (revphStickyFaults.CompressorOpen) {
            alertMessageBuilder.append("\nCompressor open fault");
        }
        if (revphStickyFaults.SolenoidOverCurrent) {
            alertMessageBuilder.append("\nSolenoid Over Current fault");
        }
        if (revphStickyFaults.Brownout) {
            alertMessageBuilder.append("\nBrownout fault");
        }
        if (revphStickyFaults.CanWarning) {
            alertMessageBuilder.append("\nCan Warning fault");
        }
        if (revphStickyFaults.CanBusOff) {
            alertMessageBuilder.append("\nCan Bus Off fault");
        }
        // if (revphStickyFaults.HasReset) {
        //     alertMessageBuilder.append("\nHas reset fault");
        // }

        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));

    }

}
