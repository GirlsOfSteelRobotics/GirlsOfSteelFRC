package com.gos.reefscape;

import edu.wpi.first.math.util.Units;

public enum PIE {
    L1(Units.inchesToMeters(18), 45),
    L2(Units.inchesToMeters(31.875), 45),
    L3(Units.inchesToMeters(47.625), 45),
    L4(Units.inchesToMeters(72), 90),
    HUMAN_PLAYER_STATION(Units.inchesToMeters(37), -35),
    FETCH_ALGAE_2(Units.inchesToMeters(39), 45),
    FETCH_ALGAE_3(Units.inchesToMeters(50), 45),
    SCORE_INTO_NET(1.5, 100),
    SCORE_INTO_PROCESSOR(.5, 40);

    public double m_height;
    public double m_angle;

    PIE(double height, double angle) {
        m_height = height;
        m_angle = angle;
    }
}
