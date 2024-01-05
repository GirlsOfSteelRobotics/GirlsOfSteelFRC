package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;
import org.littletonrobotics.frc2023.util.Alert;

import java.util.ArrayList;
import java.util.List;

public class PigeonAlerts {
    private static final String FAULT_ALERT_NAME = "pigeon";
    private static final String STICKY_ALERT_NAME = "pigeon (sticky)";

    private final Pigeon2 m_pigeon;
    private final Alert m_faultAlert;
    private final Alert m_stickyFaultAlert;

    private final List<StatusSignal<Boolean>> m_faultSignals;
    private final List<StatusSignal<Boolean>> m_stickyFaultSignals;


    public PigeonAlerts(Pigeon2 pigeon2) {
        m_pigeon = pigeon2;

        m_faultSignals = populateFaultSignals();
        m_stickyFaultSignals = populateStickyFaultSignals();

        m_faultAlert = new Alert(FAULT_ALERT_NAME, Alert.AlertType.ERROR);
        m_stickyFaultAlert = new Alert(STICKY_ALERT_NAME, Alert.AlertType.WARNING);
    }

    private List<StatusSignal<Boolean>> populateFaultSignals() {
        List<StatusSignal<Boolean>> output = new ArrayList<>();

        output.add(m_pigeon.getFault_Hardware());
        output.add(m_pigeon.getFault_Undervoltage());
        output.add(m_pigeon.getFault_BootDuringEnable());
        output.add(m_pigeon.getFault_UnlicensedFeatureInUse());
        output.add(m_pigeon.getFault_BootupAccelerometer());
        output.add(m_pigeon.getFault_BootupGyroscope());
        output.add(m_pigeon.getFault_BootupMagnetometer());
        output.add(m_pigeon.getFault_BootIntoMotion());
        output.add(m_pigeon.getFault_DataAcquiredLate());
        output.add(m_pigeon.getFault_LoopTimeSlow());
        output.add(m_pigeon.getFault_SaturatedMagnetometer());
        output.add(m_pigeon.getFault_SaturatedAccelerometer());
        output.add(m_pigeon.getFault_SaturatedGyroscope());

        return output;
    }

    private List<StatusSignal<Boolean>> populateStickyFaultSignals() {
        List<StatusSignal<Boolean>> output = new ArrayList<>();

        output.add(m_pigeon.getStickyFault_Hardware());
        output.add(m_pigeon.getStickyFault_Undervoltage());
        output.add(m_pigeon.getStickyFault_BootDuringEnable());
        output.add(m_pigeon.getStickyFault_UnlicensedFeatureInUse());
        output.add(m_pigeon.getStickyFault_BootupAccelerometer());
        output.add(m_pigeon.getStickyFault_BootupGyroscope());
        output.add(m_pigeon.getStickyFault_BootupMagnetometer());
        output.add(m_pigeon.getStickyFault_BootIntoMotion());
        output.add(m_pigeon.getStickyFault_DataAcquiredLate());
        output.add(m_pigeon.getStickyFault_LoopTimeSlow());
        output.add(m_pigeon.getStickyFault_SaturatedMagnetometer());
        output.add(m_pigeon.getStickyFault_SaturatedAccelerometer());
        output.add(m_pigeon.getStickyFault_SaturatedGyroscope());

        return output;
    }

    public void checkAlerts() {
        checkFaultList(m_faultSignals, m_faultAlert, FAULT_ALERT_NAME);
        checkFaultList(m_stickyFaultSignals, m_stickyFaultAlert, STICKY_ALERT_NAME);
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
