package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import org.littletonrobotics.frc2023.util.Alert;

import java.util.List;

public class BasePhoenix6Alerts {
    private final String m_faultAlertName;
    private final String m_stickyFaultAlertName;

    private final Alert m_faultAlert;
    private final Alert m_stickyFaultAlert;

    protected final List<StatusSignal<Boolean>> m_faultSignals;
    protected final List<StatusSignal<Boolean>> m_stickyFaultSignals;

    public BasePhoenix6Alerts(
        String faultName,
        List<StatusSignal<Boolean>> faultSignals,
        String stickyFaultName,
        List<StatusSignal<Boolean>> stickyFaultSignals) {
        m_faultAlertName = faultName;
        m_stickyFaultAlertName = stickyFaultName;

        m_faultAlert = new Alert(faultName, Alert.AlertType.ERROR);
        m_stickyFaultAlert = new Alert(stickyFaultName, Alert.AlertType.WARNING);

        m_faultSignals = faultSignals;
        m_stickyFaultSignals = stickyFaultSignals;
    }

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
