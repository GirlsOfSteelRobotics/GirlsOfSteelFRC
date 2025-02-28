package com.gos.reefscape.enums;

public enum PIEAlgae {
    FETCH_ALGAE_2(0.6, -210),
    FETCH_ALGAE_3(1.1, -210),
    SCORE_INTO_NET(1.5, -100),
    SCORE_INTO_PROCESSOR(0, -180);

    public PIESetpoint m_setpoint;

    PIEAlgae(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIEAlgae(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
