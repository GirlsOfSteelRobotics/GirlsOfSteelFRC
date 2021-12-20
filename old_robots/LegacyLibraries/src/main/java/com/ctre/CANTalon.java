package com.ctre;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

@SuppressWarnings("PMD.GodClass")
public class CANTalon extends WPI_TalonSRX {

    public CANTalon(int deviceNumber) {
        super(deviceNumber);
    }

    public void reverseSensor(boolean reverse) {
        setSensorPhase(!reverse);
    }

    public void configFwdLimitSwitchNormallyOpen(boolean b) {
        throw new UnsupportedOperationException();
    }

    public void configRevLimitSwitchNormallyOpen(boolean b) {
        throw new UnsupportedOperationException();
    }

    public void enableLimitSwitch(boolean fwd, boolean rev) {
        if (fwd && rev) {
            overrideLimitSwitchesEnable(true);
        } else if (!fwd && !rev) {
            overrideLimitSwitchesEnable(false);
        } else {
            throw new IllegalArgumentException("Fwd and reverse must be the same");
        }
    }
}
