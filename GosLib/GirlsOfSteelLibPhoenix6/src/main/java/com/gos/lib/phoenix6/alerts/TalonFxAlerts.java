package com.gos.lib.phoenix6.alerts;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for managing alerts for a {@link TalonFX} device.
 */
public class TalonFxAlerts extends BasePhoenix6Alerts {
    /**
     * Constructor
     * @param device The motor controller
     * @param motorName A name for the motor controller, used to prefix the alerts names
     */
    public TalonFxAlerts(TalonFX device, String motorName) {
        super(motorName + " Faults",
            populateFaultSignals(device),
            motorName + " Sticky Faults",
            populateStickyFaultSignals(device));
    }

    private static List<StatusSignal<Boolean>> populateFaultSignals(TalonFX device) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();
        output.add(device.getFault_Hardware());
        output.add(device.getFault_ProcTemp());
        output.add(device.getFault_DeviceTemp());
        output.add(device.getFault_Undervoltage());
        output.add(device.getFault_BootDuringEnable());
        output.add(device.getFault_UnlicensedFeatureInUse());
        output.add(device.getFault_BridgeBrownout());
        output.add(device.getFault_RemoteSensorReset());
        output.add(device.getFault_MissingDifferentialFX());
        output.add(device.getFault_RemoteSensorPosOverflow());
        output.add(device.getFault_OverSupplyV());
        output.add(device.getFault_UnstableSupplyV());
        output.add(device.getFault_ReverseHardLimit());
        output.add(device.getFault_ForwardHardLimit());
        output.add(device.getFault_ReverseSoftLimit());
        output.add(device.getFault_ForwardSoftLimit());
        output.add(device.getFault_MissingSoftLimitRemote());
        output.add(device.getFault_MissingHardLimitRemote());
        output.add(device.getFault_RemoteSensorDataInvalid());
        output.add(device.getFault_FusedSensorOutOfSync());
        output.add(device.getFault_StatorCurrLimit());
        output.add(device.getFault_SupplyCurrLimit());
        output.add(device.getFault_UsingFusedCANcoderWhileUnlicensed());
        output.add(device.getFault_StaticBrakeDisabled());
        return output;
    }

    private static List<StatusSignal<Boolean>> populateStickyFaultSignals(TalonFX device) {
        List<StatusSignal<Boolean>> output = new ArrayList<>();
        output.add(device.getStickyFault_Hardware());
        output.add(device.getStickyFault_ProcTemp());
        output.add(device.getStickyFault_DeviceTemp());
        output.add(device.getStickyFault_Undervoltage());
        output.add(device.getStickyFault_BootDuringEnable());
        output.add(device.getStickyFault_UnlicensedFeatureInUse());
        output.add(device.getStickyFault_BridgeBrownout());
        output.add(device.getStickyFault_RemoteSensorReset());
        output.add(device.getStickyFault_MissingDifferentialFX());
        output.add(device.getStickyFault_RemoteSensorPosOverflow());
        output.add(device.getStickyFault_OverSupplyV());
        output.add(device.getStickyFault_UnstableSupplyV());
        output.add(device.getStickyFault_ReverseHardLimit());
        output.add(device.getStickyFault_ForwardHardLimit());
        output.add(device.getStickyFault_ReverseSoftLimit());
        output.add(device.getStickyFault_ForwardSoftLimit());
        output.add(device.getStickyFault_MissingSoftLimitRemote());
        output.add(device.getStickyFault_MissingHardLimitRemote());
        output.add(device.getStickyFault_RemoteSensorDataInvalid());
        output.add(device.getStickyFault_FusedSensorOutOfSync());
        output.add(device.getStickyFault_StatorCurrLimit());
        output.add(device.getStickyFault_SupplyCurrLimit());
        output.add(device.getStickyFault_UsingFusedCANcoderWhileUnlicensed());
        output.add(device.getStickyFault_StaticBrakeDisabled());
        return output;
    }
}
