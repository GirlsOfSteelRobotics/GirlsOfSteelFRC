package com.gos.reefscape.enums;

import edu.wpi.first.math.util.Units;

public enum PIECoral {
    HUMAN_PLAYER_STATION(Units.inchesToMeters(0), 0),
    L1((0), 0),
    L2((0), -37.95),
    L3((0.44), -37.95),
    L4((1.10), -37.95);

    public PIESetpoint m_setpoint;

    PIECoral(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIECoral(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
