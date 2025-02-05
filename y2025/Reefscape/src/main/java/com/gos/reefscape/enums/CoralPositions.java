package com.gos.reefscape.enums;

import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;

public enum CoralPositions {
    A(ChoreoPoses.A),
    B(ChoreoPoses.B),
    C(ChoreoPoses.C),
    D(ChoreoPoses.D),
    E(ChoreoPoses.E),
    F(ChoreoPoses.F),
    G(ChoreoPoses.G),
    H(ChoreoPoses.H),
    I(ChoreoPoses.I),
    J(ChoreoPoses.J),
    K(ChoreoPoses.K),
    L(ChoreoPoses.L);

    public Pose2d m_pose;


    CoralPositions(Pose2d pose) {
        m_pose = pose;
    }
}
