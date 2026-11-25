package com.gos.lib.alerts;

import org.wpilib.hardware.hal.PowerDistributionFaults;
import org.wpilib.hardware.hal.PowerDistributionStickyFaults;
import org.wpilib.util.Alert;
import org.wpilib.hardware.power.PowerDistribution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.CyclomaticComplexity"})
public class PowerDistributionAlerts {
    private static final String ALERT_NAME = "power distribution";
    private static final String STICKY_ALERT_NAME = "power distribution (sticky) ";

    private final PowerDistribution m_powerDistribution;
    private final Alert m_alert;
    private final Alert m_alertSticky;

    private Set<Integer> m_ignoredBreakers;

    public PowerDistributionAlerts(PowerDistribution powerDistribution) {
        this(powerDistribution, new ArrayList<>());
    }

    public PowerDistributionAlerts(PowerDistribution powerDistribution, List<Integer> ignoredBreakers) {
        m_powerDistribution = powerDistribution;
        m_alert = AlertFactory.create("power distribution", Alert.Level.HIGH);
        m_alertSticky = AlertFactory.create("power distribution (sticky)", Alert.Level.MEDIUM);

        m_ignoredBreakers = new HashSet<>();
        m_ignoredBreakers.addAll(ignoredBreakers);
    }


    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.NcssCount"})
    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void checkFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(ALERT_NAME);

        PowerDistributionFaults faults = m_powerDistribution.getFaults();

        if (faults.channel0BreakerFault && !m_ignoredBreakers.contains(0)) {
            alertMessageBuilder.append("\nchannel 0 breaker error");
        }
        if (faults.channel1BreakerFault && !m_ignoredBreakers.contains(1)) {
            alertMessageBuilder.append("\nchannel 1 breaker error");
        }
        if (faults.channel2BreakerFault && !m_ignoredBreakers.contains(2)) {
            alertMessageBuilder.append("\nchannel 2 breaker error");
        }
        if (faults.channel3BreakerFault && !m_ignoredBreakers.contains(3)) {
            alertMessageBuilder.append("\nchannel 3 breaker error");
        }
        if (faults.channel4BreakerFault && !m_ignoredBreakers.contains(4)) {
            alertMessageBuilder.append("\nchannel 4 breaker error");
        }
        if (faults.channel5BreakerFault && !m_ignoredBreakers.contains(5)) {
            alertMessageBuilder.append("\nchannel 5 breaker error");
        }
        if (faults.channel6BreakerFault && !m_ignoredBreakers.contains(6)) {
            alertMessageBuilder.append("\nchannel 6 breaker error");
        }
        if (faults.channel7BreakerFault && !m_ignoredBreakers.contains(7)) {
            alertMessageBuilder.append("\nchannel 7 breaker error");
        }
        if (faults.channel8BreakerFault && !m_ignoredBreakers.contains(8)) {
            alertMessageBuilder.append("\nchannel 8 breaker error");
        }
        if (faults.channel9BreakerFault && !m_ignoredBreakers.contains(9)) {
            alertMessageBuilder.append("\nchannel 9 breaker error");
        }
        if (faults.channel10BreakerFault && !m_ignoredBreakers.contains(10)) {
            alertMessageBuilder.append("\nchannel 10 breaker error");
        }
        if (faults.channel11BreakerFault && !m_ignoredBreakers.contains(11)) {
            alertMessageBuilder.append("\nchannel 11 breaker error");
        }
        if (faults.channel12BreakerFault && !m_ignoredBreakers.contains(12)) {
            alertMessageBuilder.append("\nchannel 12 breaker error");
        }
        if (faults.channel13BreakerFault && !m_ignoredBreakers.contains(13)) {
            alertMessageBuilder.append("\nchannel 13 breaker error");
        }
        if (faults.channel14BreakerFault && !m_ignoredBreakers.contains(14)) {
            alertMessageBuilder.append("\nchannel 14 breaker error");
        }
        if (faults.channel15BreakerFault && !m_ignoredBreakers.contains(15)) {
            alertMessageBuilder.append("\nchannel 15 breaker error");
        }
        if (faults.channel16BreakerFault && !m_ignoredBreakers.contains(16)) {
            alertMessageBuilder.append("\nchannel 16 breaker error");
        }
        if (faults.channel17BreakerFault && !m_ignoredBreakers.contains(17)) {
            alertMessageBuilder.append("\nchannel 17 breaker error");
        }
        if (faults.channel18BreakerFault && !m_ignoredBreakers.contains(18)) {
            alertMessageBuilder.append("\nchannel 18 breaker error");
        }
        if (faults.channel19BreakerFault && !m_ignoredBreakers.contains(19)) {
            alertMessageBuilder.append("\nchannel 19 breaker error");
        }
        if (faults.channel20BreakerFault && !m_ignoredBreakers.contains(20)) {
            alertMessageBuilder.append("\nchannel 20 breaker error");
        }
        if (faults.channel21BreakerFault && !m_ignoredBreakers.contains(21)) {
            alertMessageBuilder.append("\nchannel 21 breaker error");
        }
        if (faults.channel22BreakerFault && !m_ignoredBreakers.contains(22)) {
            alertMessageBuilder.append("\nchannel 22 breaker error");
        }
        if (faults.channel23BreakerFault && !m_ignoredBreakers.contains(23)) {
            alertMessageBuilder.append("\nchannel 23 breaker error");
        }
        if (faults.brownout) {
            alertMessageBuilder.append("\nbrownout error");
        }
        if (faults.canWarning) {
            alertMessageBuilder.append("\ncan warning error");
        }
        if (faults.hardwareFault) {
            alertMessageBuilder.append("\nHardware fault error");
        }
        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void checkStickyFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(STICKY_ALERT_NAME);

        PowerDistributionStickyFaults stickyFaults = m_powerDistribution.getStickyFaults();

        if (stickyFaults.channel0BreakerFault && !m_ignoredBreakers.contains(0)) {
            alertMessageBuilder.append("\nchannel 0 breaker error");
        }
        if (stickyFaults.channel1BreakerFault && !m_ignoredBreakers.contains(1)) {
            alertMessageBuilder.append("\nchannel 1 breaker error");
        }
        if (stickyFaults.channel2BreakerFault && !m_ignoredBreakers.contains(2)) {
            alertMessageBuilder.append("\nchannel 2 breaker error");
        }
        if (stickyFaults.channel3BreakerFault && !m_ignoredBreakers.contains(3)) {
            alertMessageBuilder.append("\nchannel 3 breaker error");
        }
        if (stickyFaults.channel4BreakerFault && !m_ignoredBreakers.contains(4)) {
            alertMessageBuilder.append("\nchannel 4 breaker error");
        }
        if (stickyFaults.channel5BreakerFault && !m_ignoredBreakers.contains(5)) {
            alertMessageBuilder.append("\nchannel 5 breaker error");
        }
        if (stickyFaults.channel6BreakerFault && !m_ignoredBreakers.contains(6)) {
            alertMessageBuilder.append("\nchannel 6 breaker error");
        }
        if (stickyFaults.channel7BreakerFault && !m_ignoredBreakers.contains(7)) {
            alertMessageBuilder.append("\nchannel 7 breaker error");
        }
        if (stickyFaults.channel8BreakerFault && !m_ignoredBreakers.contains(8)) {
            alertMessageBuilder.append("\nchannel 8 breaker error");
        }
        if (stickyFaults.channel9BreakerFault && !m_ignoredBreakers.contains(9)) {
            alertMessageBuilder.append("\nchannel 9 breaker error");
        }
        if (stickyFaults.channel10BreakerFault && !m_ignoredBreakers.contains(10)) {
            alertMessageBuilder.append("\nchannel 10 breaker error");
        }
        if (stickyFaults.channel11BreakerFault && !m_ignoredBreakers.contains(11)) {
            alertMessageBuilder.append("\nchannel 11 breaker error");
        }
        if (stickyFaults.channel12BreakerFault && !m_ignoredBreakers.contains(12)) {
            alertMessageBuilder.append("\nchannel 12 breaker error");
        }
        if (stickyFaults.channel13BreakerFault && !m_ignoredBreakers.contains(13)) {
            alertMessageBuilder.append("\nchannel 13 breaker error");
        }
        if (stickyFaults.channel14BreakerFault && !m_ignoredBreakers.contains(14)) {
            alertMessageBuilder.append("\nchannel 14 breaker error");
        }
        if (stickyFaults.channel15BreakerFault && !m_ignoredBreakers.contains(15)) {
            alertMessageBuilder.append("\nchannel 15 breaker error");
        }
        if (stickyFaults.channel16BreakerFault && !m_ignoredBreakers.contains(16)) {
            alertMessageBuilder.append("\nchannel 16 breaker error");
        }
        if (stickyFaults.channel17BreakerFault && !m_ignoredBreakers.contains(17)) {
            alertMessageBuilder.append("\nchannel 17 breaker error");
        }
        if (stickyFaults.channel18BreakerFault && !m_ignoredBreakers.contains(18)) {
            alertMessageBuilder.append("\nchannel 18 breaker error");
        }
        if (stickyFaults.channel19BreakerFault && !m_ignoredBreakers.contains(19)) {
            alertMessageBuilder.append("\nchannel 19 breaker error");
        }
        if (stickyFaults.channel20BreakerFault && !m_ignoredBreakers.contains(20)) {
            alertMessageBuilder.append("\nchannel 20 breaker error");
        }
        if (stickyFaults.channel21BreakerFault && !m_ignoredBreakers.contains(21)) {
            alertMessageBuilder.append("\nchannel 21 breaker error");
        }
        if (stickyFaults.channel22BreakerFault && !m_ignoredBreakers.contains(22)) {
            alertMessageBuilder.append("\nchannel 22 breaker error");
        }
        if (stickyFaults.channel23BreakerFault && !m_ignoredBreakers.contains(23)) {
            alertMessageBuilder.append("\nchannel 23 breaker error");
        }
        if (stickyFaults.brownout) {
            alertMessageBuilder.append("\nBrownout fault");
        }
        if (stickyFaults.canWarning) {
            alertMessageBuilder.append("\nCan Warning fault");
        }
        if (stickyFaults.canBusOff) {
            alertMessageBuilder.append("\nCan Bus Off fault");
        }
        // if (stickyFaults.hasReset) {
        //     alertMessageBuilder.append("\nHas Reset fault");
        // }
        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));
    }
}
