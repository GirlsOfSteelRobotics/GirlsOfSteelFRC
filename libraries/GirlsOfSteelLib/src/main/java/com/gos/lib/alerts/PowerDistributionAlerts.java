package com.gos.lib.alerts;

import edu.wpi.first.hal.PowerDistributionFaults;
import edu.wpi.first.hal.PowerDistributionStickyFaults;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.PowerDistribution;

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
        m_alert = new Alert("power distribution", AlertType.kError);
        m_alertSticky = new Alert("power distribution (sticky)", AlertType.kWarning);

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

        if (faults.Channel0BreakerFault && !m_ignoredBreakers.contains(0)) {
            alertMessageBuilder.append("\nchannel 0 breaker error");
        }
        if (faults.Channel1BreakerFault && !m_ignoredBreakers.contains(1)) {
            alertMessageBuilder.append("\nchannel 1 breaker error");
        }
        if (faults.Channel2BreakerFault && !m_ignoredBreakers.contains(2)) {
            alertMessageBuilder.append("\nchannel 2 breaker error");
        }
        if (faults.Channel3BreakerFault && !m_ignoredBreakers.contains(3)) {
            alertMessageBuilder.append("\nchannel 3 breaker error");
        }
        if (faults.Channel4BreakerFault && !m_ignoredBreakers.contains(4)) {
            alertMessageBuilder.append("\nchannel 4 breaker error");
        }
        if (faults.Channel5BreakerFault && !m_ignoredBreakers.contains(5)) {
            alertMessageBuilder.append("\nchannel 5 breaker error");
        }
        if (faults.Channel6BreakerFault && !m_ignoredBreakers.contains(6)) {
            alertMessageBuilder.append("\nchannel 6 breaker error");
        }
        if (faults.Channel7BreakerFault && !m_ignoredBreakers.contains(7)) {
            alertMessageBuilder.append("\nchannel 7 breaker error");
        }
        if (faults.Channel8BreakerFault && !m_ignoredBreakers.contains(8)) {
            alertMessageBuilder.append("\nchannel 8 breaker error");
        }
        if (faults.Channel9BreakerFault && !m_ignoredBreakers.contains(9)) {
            alertMessageBuilder.append("\nchannel 9 breaker error");
        }
        if (faults.Channel10BreakerFault && !m_ignoredBreakers.contains(10)) {
            alertMessageBuilder.append("\nchannel 10 breaker error");
        }
        if (faults.Channel11BreakerFault && !m_ignoredBreakers.contains(11)) {
            alertMessageBuilder.append("\nchannel 11 breaker error");
        }
        if (faults.Channel12BreakerFault && !m_ignoredBreakers.contains(12)) {
            alertMessageBuilder.append("\nchannel 12 breaker error");
        }
        if (faults.Channel13BreakerFault && !m_ignoredBreakers.contains(13)) {
            alertMessageBuilder.append("\nchannel 13 breaker error");
        }
        if (faults.Channel14BreakerFault && !m_ignoredBreakers.contains(14)) {
            alertMessageBuilder.append("\nchannel 14 breaker error");
        }
        if (faults.Channel15BreakerFault && !m_ignoredBreakers.contains(15)) {
            alertMessageBuilder.append("\nchannel 15 breaker error");
        }
        if (faults.Channel16BreakerFault && !m_ignoredBreakers.contains(16)) {
            alertMessageBuilder.append("\nchannel 16 breaker error");
        }
        if (faults.Channel17BreakerFault && !m_ignoredBreakers.contains(17)) {
            alertMessageBuilder.append("\nchannel 17 breaker error");
        }
        if (faults.Channel18BreakerFault && !m_ignoredBreakers.contains(18)) {
            alertMessageBuilder.append("\nchannel 18 breaker error");
        }
        if (faults.Channel19BreakerFault && !m_ignoredBreakers.contains(19)) {
            alertMessageBuilder.append("\nchannel 19 breaker error");
        }
        if (faults.Channel20BreakerFault && !m_ignoredBreakers.contains(20)) {
            alertMessageBuilder.append("\nchannel 20 breaker error");
        }
        if (faults.Channel21BreakerFault && !m_ignoredBreakers.contains(21)) {
            alertMessageBuilder.append("\nchannel 21 breaker error");
        }
        if (faults.Channel22BreakerFault && !m_ignoredBreakers.contains(22)) {
            alertMessageBuilder.append("\nchannel 22 breaker error");
        }
        if (faults.Channel23BreakerFault && !m_ignoredBreakers.contains(23)) {
            alertMessageBuilder.append("\nchannel 23 breaker error");
        }
        if (faults.Brownout) {
            alertMessageBuilder.append("\nbrownout error");
        }
        if (faults.CanWarning) {
            alertMessageBuilder.append("\ncan warning error");
        }
        if (faults.HardwareFault) {
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

        if (stickyFaults.Channel0BreakerFault && !m_ignoredBreakers.contains(0)) {
            alertMessageBuilder.append("\nchannel 0 breaker error");
        }
        if (stickyFaults.Channel1BreakerFault && !m_ignoredBreakers.contains(1)) {
            alertMessageBuilder.append("\nchannel 1 breaker error");
        }
        if (stickyFaults.Channel2BreakerFault && !m_ignoredBreakers.contains(2)) {
            alertMessageBuilder.append("\nchannel 2 breaker error");
        }
        if (stickyFaults.Channel3BreakerFault && !m_ignoredBreakers.contains(3)) {
            alertMessageBuilder.append("\nchannel 3 breaker error");
        }
        if (stickyFaults.Channel4BreakerFault && !m_ignoredBreakers.contains(4)) {
            alertMessageBuilder.append("\nchannel 4 breaker error");
        }
        if (stickyFaults.Channel5BreakerFault && !m_ignoredBreakers.contains(5)) {
            alertMessageBuilder.append("\nchannel 5 breaker error");
        }
        if (stickyFaults.Channel6BreakerFault && !m_ignoredBreakers.contains(6)) {
            alertMessageBuilder.append("\nchannel 6 breaker error");
        }
        if (stickyFaults.Channel7BreakerFault && !m_ignoredBreakers.contains(7)) {
            alertMessageBuilder.append("\nchannel 7 breaker error");
        }
        if (stickyFaults.Channel8BreakerFault && !m_ignoredBreakers.contains(8)) {
            alertMessageBuilder.append("\nchannel 8 breaker error");
        }
        if (stickyFaults.Channel9BreakerFault && !m_ignoredBreakers.contains(9)) {
            alertMessageBuilder.append("\nchannel 9 breaker error");
        }
        if (stickyFaults.Channel10BreakerFault && !m_ignoredBreakers.contains(10)) {
            alertMessageBuilder.append("\nchannel 10 breaker error");
        }
        if (stickyFaults.Channel11BreakerFault && !m_ignoredBreakers.contains(11)) {
            alertMessageBuilder.append("\nchannel 11 breaker error");
        }
        if (stickyFaults.Channel12BreakerFault && !m_ignoredBreakers.contains(12)) {
            alertMessageBuilder.append("\nchannel 12 breaker error");
        }
        if (stickyFaults.Channel13BreakerFault && !m_ignoredBreakers.contains(13)) {
            alertMessageBuilder.append("\nchannel 13 breaker error");
        }
        if (stickyFaults.Channel14BreakerFault && !m_ignoredBreakers.contains(14)) {
            alertMessageBuilder.append("\nchannel 14 breaker error");
        }
        if (stickyFaults.Channel15BreakerFault && !m_ignoredBreakers.contains(15)) {
            alertMessageBuilder.append("\nchannel 15 breaker error");
        }
        if (stickyFaults.Channel16BreakerFault && !m_ignoredBreakers.contains(16)) {
            alertMessageBuilder.append("\nchannel 16 breaker error");
        }
        if (stickyFaults.Channel17BreakerFault && !m_ignoredBreakers.contains(17)) {
            alertMessageBuilder.append("\nchannel 17 breaker error");
        }
        if (stickyFaults.Channel18BreakerFault && !m_ignoredBreakers.contains(18)) {
            alertMessageBuilder.append("\nchannel 18 breaker error");
        }
        if (stickyFaults.Channel19BreakerFault && !m_ignoredBreakers.contains(19)) {
            alertMessageBuilder.append("\nchannel 19 breaker error");
        }
        if (stickyFaults.Channel20BreakerFault && !m_ignoredBreakers.contains(20)) {
            alertMessageBuilder.append("\nchannel 20 breaker error");
        }
        if (stickyFaults.Channel21BreakerFault && !m_ignoredBreakers.contains(21)) {
            alertMessageBuilder.append("\nchannel 21 breaker error");
        }
        if (stickyFaults.Channel22BreakerFault && !m_ignoredBreakers.contains(22)) {
            alertMessageBuilder.append("\nchannel 22 breaker error");
        }
        if (stickyFaults.Channel23BreakerFault && !m_ignoredBreakers.contains(23)) {
            alertMessageBuilder.append("\nchannel 23 breaker error");
        }
        if (stickyFaults.Brownout) {
            alertMessageBuilder.append("\nBrownout fault");
        }
        if (stickyFaults.CanWarning) {
            alertMessageBuilder.append("\nCan Warning fault");
        }
        if (stickyFaults.CanBusOff) {
            alertMessageBuilder.append("\nCan Bus Off fault");
        }
        // if (stickyFaults.HasReset) {
        //     alertMessageBuilder.append("\nHas Reset fault");
        // }
        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));
    }
}
