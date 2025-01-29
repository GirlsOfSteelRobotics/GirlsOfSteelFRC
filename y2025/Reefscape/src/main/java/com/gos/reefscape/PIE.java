package com.gos.reefscape;

import edu.wpi.first.math.util.Units;

public enum PIE {
    levelOne(Units.inchesToMeters(18), 45),
    levelTwo(Units.inchesToMeters(31.875), 45),
    levelThree(Units.inchesToMeters(47.625), 45),
    levelFour(Units.inchesToMeters(72), 90),
    scoreIntoNet(1.5, 100),
    scoreIntoProcessor(.5, 40);

    public double m_height;
    public double m_angle;

    PIE(double height, double angle) {
        m_height = height;
        m_angle = angle;
    }
}
