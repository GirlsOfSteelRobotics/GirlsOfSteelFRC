package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

import java.util.List;

/**
 * Base class for Phoenix6 alerts. Basically just a holder for the fault and sticky fault signals.
 */
public class BasePhoenix6Alerts {
    private final String m_faultAlertName;
    private final String m_stickyFaultAlertName;

    private final Alert m_faultAlert;
    private final Alert m_stickyFaultAlert;

    protected final List<StatusSignal<Boolean>> m_faultSignals;
    protected final List<StatusSignal<Boolean>> m_stickyFaultSignals;

    /**
     * Constructor
     * @param faultName The name to use in the alert if there is a fault
     * @param faultSignals A list of signals for faults
     * @param stickyFaultName The name to use in the alert if there is a sticky fault
     * @param stickyFaultSignals A list of signals for sticky faults
     */
    public BasePhoenix6Alerts(
        String faultName,
        List<StatusSignal<Boolean>> faultSignals,
        String stickyFaultName,
        List<StatusSignal<Boolean>> stickyFaultSignals) {
        m_faultAlertName = faultName;
        m_stickyFaultAlertName = stickyFaultName;

        m_faultAlert = new Alert(faultName, AlertType.kError);
        m_stickyFaultAlert = new Alert(stickyFaultName, AlertType.kWarning);

        m_faultSignals = faultSignals;
        m_stickyFaultSignals = stickyFaultSignals;
    }

    /**
     * Checks all the signals for both faults and sticky faults
     */
    public void checkAlerts() {
        checkFaultList(m_faultSignals, m_faultAlert, m_faultAlertName);
        checkFaultList(m_stickyFaultSignals, m_stickyFaultAlert, m_stickyFaultAlertName);
    }


    @SuppressWarnings({"PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    private void checkFaultList(List<StatusSignal<Boolean>> statusSignals, Alert alert, String alertName) {
        StringBuilder alertMessageBuilder = new StringBuilder(700);
        alertMessageBuilder.append(alertName);

        for (StatusSignal<Boolean> signal : statusSignals) {
            signal.refresh();
            if (signal.getValue()) {
                alertMessageBuilder.append(signal.getName());
            }
        }

        String alertMessage = alertMessageBuilder.toString();
        alert.setText(alertMessage);

        alert.set(!alertName.equals(alertMessage));
    }

}
