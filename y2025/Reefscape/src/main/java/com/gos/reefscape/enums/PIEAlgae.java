package com.gos.reefscape.enums;

public enum PIEAlgae {
    FETCH_ALGAE_2(0.66, -219),
    FETCH_ALGAE_3(0.8, -167),
    SCORE_INTO_NET(1.56, -68),
    SCORE_INTO_PROCESSOR(0.13, -210),
    ALGAE_LOLLIPOP(0.25, -220);

    public PIESetpoint m_setpoint;

    PIEAlgae(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIEAlgae(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
