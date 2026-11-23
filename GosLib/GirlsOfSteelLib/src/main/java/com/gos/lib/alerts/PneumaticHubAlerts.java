package com.gos.lib.alerts;

import org.wpilib.hardware.hal.REVPHFaults;
import org.wpilib.hardware.hal.REVPHStickyFaults;
import org.wpilib.util.Alert;
import org.wpilib.hardware.pneumatic.PneumaticHub;

public class PneumaticHubAlerts {
    private static final String ALERT_NAME = "pneumatic hub";
    private static final String STICKY_ALERT_NAME = "pneumatic hub (sticky) ";

    private final PneumaticHub m_pneumaticHub;
    private final Alert m_alert;
    private final Alert m_alertSticky;

    public PneumaticHubAlerts(PneumaticHub pneumaticHub) {
        m_pneumaticHub = pneumaticHub;
        m_alert = AlertFactory.create("pneumatic hub", Alert.Level.HIGH);
        m_alertSticky = AlertFactory.create("pneumatic hub (sticky) ", Alert.Level.MEDIUM);
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
        if (faults.channel0Fault) {
            alertMessageBuilder.append("\nchannel 0 error");
        }
        if (faults.channel1Fault) {
            alertMessageBuilder.append("\nchannel 1 error");
        }
        if (faults.channel2Fault) {
            alertMessageBuilder.append("\nchannel 2 error");
        }
        if (faults.channel3Fault) {
            alertMessageBuilder.append("\nchannel 3 error");
        }
        if (faults.channel4Fault) {
            alertMessageBuilder.append("\nchannel 4 error");
        }
        if (faults.channel5Fault) {
            alertMessageBuilder.append("\nchannel 5 error");
        }
        if (faults.channel6Fault) {
            alertMessageBuilder.append("\nchannel 6 error");
        }
        if (faults.channel7Fault) {
            alertMessageBuilder.append("\nchannel 7 error");
        }
        if (faults.channel8Fault) {
            alertMessageBuilder.append("\nchannel 8 error");
        }
        if (faults.channel9Fault) {
            alertMessageBuilder.append("\nchannel 9 error");
        }
        if (faults.channel10Fault) {
            alertMessageBuilder.append("\nchannel 10 error");
        }
        if (faults.channel11Fault) {
            alertMessageBuilder.append("\nchannel 11 error");
        }
        if (faults.channel12Fault) {
            alertMessageBuilder.append("\nchannel 12 error");
        }
        if (faults.channel13Fault) {
            alertMessageBuilder.append("\nchannel 13 error");
        }
        if (faults.channel14Fault) {
            alertMessageBuilder.append("\nchannel 14 error");
        }
        if (faults.channel15Fault) {
            alertMessageBuilder.append("\nchannel 15 error");
        }
        if (faults.channel6Fault) {
            alertMessageBuilder.append("\nchannel 16 error");
        }
        if (faults.brownout) {
            alertMessageBuilder.append("\nbrownout");
        }
        if (faults.compressorOverCurrent) {
            alertMessageBuilder.append("\ncompressor over current");
        }
        if (faults.canWarning) {
            alertMessageBuilder.append("\ncan warning");
        }
        if (faults.solenoidOverCurrent) {
            alertMessageBuilder.append("\nsolenoid over current");
        }
        if (faults.compressorOpen) {
            alertMessageBuilder.append("\ncompressor open");
        }
        if (faults.hardwareFault) {
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

        if (revphStickyFaults.compressorOverCurrent) {
            alertMessageBuilder.append("\nCompressor Over Current fault");
        }
        if (revphStickyFaults.compressorOpen) {
            alertMessageBuilder.append("\nCompressor open fault");
        }
        if (revphStickyFaults.solenoidOverCurrent) {
            alertMessageBuilder.append("\nSolenoid Over Current fault");
        }
        if (revphStickyFaults.brownout) {
            alertMessageBuilder.append("\nBrownout fault");
        }
        if (revphStickyFaults.canWarning) {
            alertMessageBuilder.append("\nCan Warning fault");
        }
        if (revphStickyFaults.canBusOff) {
            alertMessageBuilder.append("\nCan Bus Off fault");
        }
        // if (revphStickyFaults.hasReset) {
        //     alertMessageBuilder.append("\nHas reset fault");
        // }

        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));

    }

}
