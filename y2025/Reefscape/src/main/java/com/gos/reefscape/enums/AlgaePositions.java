package com.gos.reefscape.enums;

import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;

public enum AlgaePositions {
    AB(ChoreoPoses.AB, PIE.FETCH_ALGAE_3),
    CD(ChoreoPoses.CD, PIE.FETCH_ALGAE_2),
    EF(ChoreoPoses.EF, PIE.FETCH_ALGAE_3),
    GH(ChoreoPoses.GH, PIE.FETCH_ALGAE_2),
    IJ(ChoreoPoses.IJ, PIE.FETCH_ALGAE_3),
    KL(ChoreoPoses.KL, PIE.FETCH_ALGAE_2);


    public Pose2d m_pose;
    public PIE m_algaePosition;


    AlgaePositions(Pose2d pose, PIE algaePosition) {
        m_pose = pose;
        m_algaePosition = algaePosition;

    }
}
