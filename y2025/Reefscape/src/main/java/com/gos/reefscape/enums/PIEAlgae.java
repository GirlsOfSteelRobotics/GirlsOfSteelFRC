package com.gos.reefscape.enums;

public enum PIEAlgae {
    FETCH_ALGAE_2(0.45, -167),
    FETCH_ALGAE_3(0.68, -167),
    SCORE_INTO_NET(1.5, -100),
    SCORE_INTO_PROCESSOR(0, -203
    );

    public PIESetpoint m_setpoint;

    PIEAlgae(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIEAlgae(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
