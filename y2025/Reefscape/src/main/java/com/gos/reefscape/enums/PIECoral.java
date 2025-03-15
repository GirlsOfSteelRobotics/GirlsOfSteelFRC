package com.gos.reefscape.enums;

import edu.wpi.first.math.util.Units;

public enum PIECoral {
    HUMAN_PLAYER_STATION(Units.inchesToMeters(0), -15),
    L1(0, -90),
    L2(0.23, -48),
    L3(0.67, -48.95),
    L4(1.17, -90);


    public PIESetpoint m_setpoint;

    PIECoral(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIECoral(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
