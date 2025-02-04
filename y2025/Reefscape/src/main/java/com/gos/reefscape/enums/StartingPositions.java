package com.gos.reefscape.enums;

import edu.wpi.first.math.geometry.Rotation2d;

public enum StartingPositions {
    LEFT(7.84056282043457, 6.1349568367004395, Rotation2d.fromRadians(0)),
    RIGHT(7.84056282043457, 1.8680174350738525, Rotation2d.fromRadians(0)),
    CENTER(7.84056282043457, 4.001668930053711, Rotation2d.fromRadians(0));


    public double m_xPosition;
    public double m_yPosition;
    public Rotation2d m_rotation;

    StartingPositions(double x, double y, Rotation2d rotation) {
        m_xPosition = x;
        m_yPosition = y;
        m_rotation = rotation;

    }

}

