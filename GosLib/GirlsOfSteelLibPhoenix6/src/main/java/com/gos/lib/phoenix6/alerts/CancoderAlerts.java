package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for managing alerts for a {@link CANcoder} device.
 */
public class CancoderAlerts extends BasePhoenix6Alerts {
    /**
     * Constructor.
     * @param device The cancoder
     */
    public CancoderAlerts(CANcoder device) {
        super("CANcoder " + device.getDeviceID() + " Faults",
            populateFaultSignals(device),
            "CANcoder " + device.getDeviceID() + " Sticky Faults",
            populateStickyFaultSignals(device));
    }

    private static List<StatusSignal<Boolean>> populateFaultSignals(CANcoder device) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();
        output.add(device.getFault_Hardware());
        output.add(device.getFault_Undervoltage());
        output.add(device.getFault_BootDuringEnable());
        output.add(device.getFault_UnlicensedFeatureInUse());
        output.add(device.getFault_BadMagnet());
        return output;
    }

    private static List<StatusSignal<Boolean>> populateStickyFaultSignals(CANcoder device) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();
        output.add(device.getStickyFault_Hardware());
        output.add(device.getStickyFault_Undervoltage());
        output.add(device.getStickyFault_BootDuringEnable());
        output.add(device.getStickyFault_UnlicensedFeatureInUse());
        output.add(device.getStickyFault_BadMagnet());
        return output;
    }
}
