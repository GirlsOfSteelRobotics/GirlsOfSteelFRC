package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for managing alerts for a {@link Pigeon2} device.
 */
public class PigeonAlerts extends BasePhoenix6Alerts {
    private static final String FAULT_ALERT_NAME = "pigeon";
    private static final String STICKY_ALERT_NAME = "pigeon (sticky)";

    /**
     * Constructor
     * @param pigeon2 the pigeon
     */
    public PigeonAlerts(Pigeon2 pigeon2) {
        super(FAULT_ALERT_NAME, populateFaultSignals(pigeon2), STICKY_ALERT_NAME, populateStickyFaultSignals(pigeon2));
    }

    private static List<StatusSignal<Boolean>> populateFaultSignals(Pigeon2 pigeon) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();

        output.add(pigeon.getFault_Hardware());
        output.add(pigeon.getFault_Undervoltage());
        output.add(pigeon.getFault_BootDuringEnable());
        output.add(pigeon.getFault_UnlicensedFeatureInUse());
        output.add(pigeon.getFault_BootupAccelerometer());
        output.add(pigeon.getFault_BootupGyroscope());
        output.add(pigeon.getFault_BootupMagnetometer());
        output.add(pigeon.getFault_BootIntoMotion());
        output.add(pigeon.getFault_DataAcquiredLate());
        output.add(pigeon.getFault_LoopTimeSlow());
        output.add(pigeon.getFault_SaturatedMagnetometer());
        output.add(pigeon.getFault_SaturatedAccelerometer());
        output.add(pigeon.getFault_SaturatedGyroscope());

        return output;
    }

    private static List<StatusSignal<Boolean>> populateStickyFaultSignals(Pigeon2 pigeon) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();

        output.add(pigeon.getStickyFault_Hardware());
        output.add(pigeon.getStickyFault_Undervoltage());
        output.add(pigeon.getStickyFault_BootDuringEnable());
        output.add(pigeon.getStickyFault_UnlicensedFeatureInUse());
        output.add(pigeon.getStickyFault_BootupAccelerometer());
        output.add(pigeon.getStickyFault_BootupGyroscope());
        output.add(pigeon.getStickyFault_BootupMagnetometer());
        output.add(pigeon.getStickyFault_BootIntoMotion());
        output.add(pigeon.getStickyFault_DataAcquiredLate());
        output.add(pigeon.getStickyFault_LoopTimeSlow());
        output.add(pigeon.getStickyFault_SaturatedMagnetometer());
        output.add(pigeon.getStickyFault_SaturatedAccelerometer());
        output.add(pigeon.getStickyFault_SaturatedGyroscope());

        return output;
    }
}
