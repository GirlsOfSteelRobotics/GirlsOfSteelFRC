package com.gos.reefscape.enums;

import edu.wpi.first.math.geometry.Rotation2d;

public enum AlgaePositions {
    EF(5.147095680236816, 2.9247190952301025, Rotation2d.fromRadians(-1.0516503687020464)),
    GH(5.739912986755371, 3.987903594970703, Rotation2d.fromRadians(0.0));

    public double m_xPosition;
    public double m_yPosition;
    public Rotation2d m_rotation;

    AlgaePositions(double x, double y, Rotation2d rotation) {
        m_xPosition = x;
        m_yPosition = y;
        m_rotation = rotation;

    }
}
