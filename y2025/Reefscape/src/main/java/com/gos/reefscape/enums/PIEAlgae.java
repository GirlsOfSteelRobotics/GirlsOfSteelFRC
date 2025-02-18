package com.gos.reefscape.enums;

import edu.wpi.first.math.util.Units;

public enum PIEAlgae {
    FETCH_ALGAE_2(Units.inchesToMeters(39), -45),
    FETCH_ALGAE_3(Units.inchesToMeters(50), -45),
    SCORE_INTO_NET(1.5, -100),
    SCORE_INTO_PROCESSOR(.5, -40);

    public PIESetpoint m_setpoint;

    PIEAlgae(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIEAlgae(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
