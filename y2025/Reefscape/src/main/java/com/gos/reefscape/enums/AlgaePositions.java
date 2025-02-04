package com.gos.reefscape.enums;

import choreo.Choreo;
import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public enum AlgaePositions {
    EF(ChoreoPoses.EF),
    GH(ChoreoPoses.GH);

    public Pose2d m_pose;


    AlgaePositions(Pose2d pose) {
        m_pose = pose;

    }
}
