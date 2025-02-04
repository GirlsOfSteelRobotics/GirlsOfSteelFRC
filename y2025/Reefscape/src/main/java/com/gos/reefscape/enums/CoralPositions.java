package com.gos.reefscape.enums;

import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public enum CoralPositions {
    C(ChoreoPoses.C),
    E(ChoreoPoses.E),
    J(ChoreoPoses.J),
    L(ChoreoPoses.L);

    public Pose2d m_pose;


    CoralPositions(Pose2d pose) {
        m_pose = pose;
    }
}
