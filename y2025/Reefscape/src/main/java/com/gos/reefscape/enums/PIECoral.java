package com.gos.reefscape.enums;

import edu.wpi.first.math.util.Units;

public enum PIECoral {
    HUMAN_PLAYER_STATION(Units.inchesToMeters(37), -35),
    L1(Units.inchesToMeters(0), 45),
    L2(Units.inchesToMeters(0), 45),
    L3(Units.inchesToMeters(15.5), 45),
    L4(Units.inchesToMeters(30), 90);

    public double m_height;
    public double m_angle;

    PIECoral(double height, double angle) {
        m_height = height;
        m_angle = angle;
    }
}
