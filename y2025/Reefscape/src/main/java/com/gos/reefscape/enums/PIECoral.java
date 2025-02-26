package com.gos.reefscape.enums;

import edu.wpi.first.math.util.Units;

public enum PIECoral {
    HUMAN_PLAYER_STATION(Units.inchesToMeters(0), -24),
    L1(0, -90),
    L2(0.14, -45.95),
    L3(0.6, -45.95),
    L4(1.17, -90);


    public PIESetpoint m_setpoint;

    PIECoral(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIECoral(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
