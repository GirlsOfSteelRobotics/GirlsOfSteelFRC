package com.gos.lib.checklists;

import edu.wpi.first.hal.PowerDistributionFaults;
import edu.wpi.first.hal.PowerDistributionStickyFaults;
import edu.wpi.first.wpilibj.PowerDistribution;
import org.littletonrobotics.frc2023.util.Alert;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PowerDistributionAlerts {
    private static final String ALERT_NAME = "power distribution";
    private static final String STICKY_ALERT_NAME = "power distribution (sticky) ";

    private final PowerDistribution m_powerDistribution;
    private final Alert m_alert;
    private final Alert m_alertSticky;

    public PowerDistributionAlerts(PowerDistribution powerDistribution) {
        m_powerDistribution = powerDistribution;
        m_alert = new Alert("power distribution", Alert.AlertType.ERROR);
        m_alertSticky = new Alert("power distribution (sticky)", Alert.AlertType.ERROR);
    }


    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.NcssCount"})
    public void checkAlerts() {
        checkFaults();
        checkStickyFaults();
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void checkFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(ALERT_NAME);

        PowerDistributionFaults faults = m_powerDistribution.getFaults();

        if (faults.Channel0BreakerFault) {
            alertMessageBuilder.append(" channel 0 breaker error");
        }
        if (faults.Channel1BreakerFault) {
            alertMessageBuilder.append(" channel 1 breaker error");
        }
        if (faults.Channel2BreakerFault) {
            alertMessageBuilder.append(" channel 2 breaker error");
        }
        if (faults.Channel3BreakerFault) {
            alertMessageBuilder.append(" channel 3 breaker error");
        }
        if (faults.Channel4BreakerFault) {
            alertMessageBuilder.append(" channel 4 breaker error");
        }
        if (faults.Channel5BreakerFault) {
            alertMessageBuilder.append(" channel 5 breaker error");
        }
        if (faults.Channel6BreakerFault) {
            alertMessageBuilder.append(" channel 6 breaker error");
        }
        if (faults.Channel7BreakerFault) {
            alertMessageBuilder.append(" channel 7 breaker error");
        }
        if (faults.Channel8BreakerFault) {
            alertMessageBuilder.append(" channel 8 breaker error");
        }
        if (faults.Channel9BreakerFault) {
            alertMessageBuilder.append(" channel 9 breaker error");
        }
        if (faults.Channel10BreakerFault) {
            alertMessageBuilder.append(" channel 10 breaker error");
        }
        if (faults.Channel11BreakerFault) {
            alertMessageBuilder.append(" channel 11 breaker error");
        }
        if (faults.Channel12BreakerFault) {
            alertMessageBuilder.append(" channel 12 breaker error");
        }
        if (faults.Channel13BreakerFault) {
            alertMessageBuilder.append(" channel 13 breaker error");
        }
        if (faults.Channel14BreakerFault) {
            alertMessageBuilder.append(" channel 14 breaker error");
        }
        if (faults.Channel15BreakerFault) {
            alertMessageBuilder.append(" channel 15 breaker error");
        }
        if (faults.Channel16BreakerFault) {
            alertMessageBuilder.append(" channel 16 breaker error");
        }
        if (faults.Channel17BreakerFault) {
            alertMessageBuilder.append(" channel 17 breaker error");
        }
        if (faults.Channel18BreakerFault) {
            alertMessageBuilder.append(" channel 18 breaker error");
        }
        if (faults.Channel19BreakerFault) {
            alertMessageBuilder.append(" channel 19 breaker error");
        }
        if (faults.Channel20BreakerFault) {
            alertMessageBuilder.append(" channel 20 breaker error");
        }
        if (faults.Channel21BreakerFault) {
            alertMessageBuilder.append(" channel 21 breaker error");
        }
        if (faults.Channel22BreakerFault) {
            alertMessageBuilder.append(" channel 22 breaker error");
        }
        if (faults.Channel23BreakerFault) {
            alertMessageBuilder.append(" channel 23 breaker error");
        }
        if (faults.Brownout) {
            alertMessageBuilder.append(" brownout error");
        }
        if (faults.CanWarning) {
            alertMessageBuilder.append("can warning error");
        }
        if (faults.HardwareFault) {
            alertMessageBuilder.append("Hardware fault error");
        }
        String alertMessage = alertMessageBuilder.toString();
        m_alert.setText(alertMessage);

        m_alert.set(!ALERT_NAME.equals(alertMessage));
    }

    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public void checkStickyFaults() {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(STICKY_ALERT_NAME);

        PowerDistributionStickyFaults stickyFaults = m_powerDistribution.getStickyFaults();

        if (stickyFaults.Channel0BreakerFault) {
            alertMessageBuilder.append("Channel 0 breaker error");
        }
        if (stickyFaults.Channel1BreakerFault) {
            alertMessageBuilder.append("Channel 1 breaker error");
        }
        if (stickyFaults.Channel2BreakerFault) {
            alertMessageBuilder.append("Channel 2 breaker error");
        }
        if (stickyFaults.Channel3BreakerFault) {
            alertMessageBuilder.append("Channel 3 breaker fault");
        }
        if (stickyFaults.Channel4BreakerFault) {
            alertMessageBuilder.append("Channel 4 breaker fault");
        }
        if (stickyFaults.Channel5BreakerFault) {
            alertMessageBuilder.append("Channel 5 breaker fault");
        }
        if (stickyFaults.Channel6BreakerFault) {
            alertMessageBuilder.append("Channel 6 breaker fault");
        }
        if (stickyFaults.Channel7BreakerFault) {
            alertMessageBuilder.append("Channel 7 breaker fault");
        }
        if (stickyFaults.Channel8BreakerFault) {
            alertMessageBuilder.append("Channel 8 breaker fault");
        }
        if (stickyFaults.Channel9BreakerFault) {
            alertMessageBuilder.append("Channel 9 breaker fault");
        }
        if (stickyFaults.Channel10BreakerFault) {
            alertMessageBuilder.append("Channel 10 breaker fault");
        }
        if (stickyFaults.Channel11BreakerFault) {
            alertMessageBuilder.append("Channel 11 breaker fault");
        }
        if (stickyFaults.Channel12BreakerFault) {
            alertMessageBuilder.append("Channel 12 breaker fault");
        }
        if (stickyFaults.Channel13BreakerFault) {
            alertMessageBuilder.append("Channel 13 breaker fault");
        }
        if (stickyFaults.Channel14BreakerFault) {
            alertMessageBuilder.append("Channel 14 breaker fault");
        }
        if (stickyFaults.Channel15BreakerFault) {
            alertMessageBuilder.append("Channel 15 breaker fault");
        }
        if (stickyFaults.Channel16BreakerFault) {
            alertMessageBuilder.append("Channel 16 breaker fault");
        }
        if (stickyFaults.Channel17BreakerFault) {
            alertMessageBuilder.append("Channel 17 breaker fault");
        }
        if (stickyFaults.Channel18BreakerFault) {
            alertMessageBuilder.append("Channel 18 breaker fault");
        }
        if (stickyFaults.Channel19BreakerFault) {
            alertMessageBuilder.append("Channel 19 breaker fault");
        }
        if (stickyFaults.Channel20BreakerFault) {
            alertMessageBuilder.append("Channel 20 breaker fault");
        }
        if (stickyFaults.Channel21BreakerFault) {
            alertMessageBuilder.append("Channel 21 breaker fault");
        }
        if (stickyFaults.Channel22BreakerFault) {
            alertMessageBuilder.append("Channel 22 breaker fault");
        }
        if (stickyFaults.Channel23BreakerFault) {
            alertMessageBuilder.append("Channel 23 breaker fault");
        }
        if (stickyFaults.Brownout) {
            alertMessageBuilder.append("Brownout fault");
        }
        if (stickyFaults.CanWarning) {
            alertMessageBuilder.append("Can Warning fault");
        }
        if (stickyFaults.CanBusOff) {
            alertMessageBuilder.append("Can Bus Off fault");
        }
        if (stickyFaults.HasReset) {
            alertMessageBuilder.append("Has Reset fault");
        }
        String alertMessage = alertMessageBuilder.toString();
        m_alertSticky.setText(alertMessage);

        m_alertSticky.set(!STICKY_ALERT_NAME.equals(alertMessage));
    }
}
