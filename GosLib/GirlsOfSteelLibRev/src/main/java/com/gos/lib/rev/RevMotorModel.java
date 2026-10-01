package com.gos.lib.rev;

import com.gos.lib.rev.RevMotorConstants.NeoMotorConstants;
import com.gos.lib.rev.RevMotorConstants.VortexMotorConstants;

public enum RevMotorModel {
    NEO(NeoMotorConstants.FREE_SPEED_RPM / 60),
    VORTEX(VortexMotorConstants.FREE_SPEED_RPM / 60);

    public final double m_freeSpeedRps;

    RevMotorModel(double freeSpeedRps) {
        m_freeSpeedRps = freeSpeedRps;
    }
}
