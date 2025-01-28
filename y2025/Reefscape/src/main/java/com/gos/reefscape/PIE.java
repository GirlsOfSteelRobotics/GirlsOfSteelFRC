package com.gos.reefscape;

public enum PIE {
    levelOne(.25, 45),
    levelTwo(.5,45),
    levelThree(.75,45),
    levelFour(1,90),
    scoreIntoNet(1.5,100),
    scoreIntoProcessor(.5,40),
    humanPlayerStation(1,90),
    collectAlgae(1,45);

    public double m_height;
    public double m_angle;

    PIE(double height, double angle) {
        m_height = height;
        m_angle = angle;
    }
}
