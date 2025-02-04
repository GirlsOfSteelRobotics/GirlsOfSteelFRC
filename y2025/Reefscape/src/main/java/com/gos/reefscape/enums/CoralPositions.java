package com.gos.reefscape.enums;

import edu.wpi.first.math.geometry.Rotation2d;

public enum CoralPositions {
    C(3.606261730194092, 3.079380989074707, Rotation2d.fromRadians(-2.1662063752267593)),
    E(4.955280303955078, 2.8178365230560303, Rotation2d.fromRadians(-1.0471975511965976)),
    J(4.943519115447998, 5.226694107055664, Rotation2d.fromRadians(1.0370881817864992)),
    L(3.618795394897461, 4.982372760772705, Rotation2d.fromRadians(2.095591826496163));

    public double m_xPosition;
    public double m_yPosition;
    public Rotation2d m_rotation;

    CoralPositions(double x, double y, Rotation2d rotation) {
        m_xPosition = x;
        m_yPosition = y;
        m_rotation = rotation;

    }
}
