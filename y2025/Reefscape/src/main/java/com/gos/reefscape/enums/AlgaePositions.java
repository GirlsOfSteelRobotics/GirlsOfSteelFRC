package com.gos.reefscape.enums;

import com.gos.reefscape.generated.ChoreoPoses;
import com.gos.reefscape.MaybeFlippedPose2d;

public enum AlgaePositions {
    AB(ChoreoPoses.AB, PIEAlgae.FETCH_ALGAE_3, CoralPositions.A, CoralPositions.B),
    CD(ChoreoPoses.CD, PIEAlgae.FETCH_ALGAE_2, CoralPositions.C, CoralPositions.D),
    EF(ChoreoPoses.EF, PIEAlgae.FETCH_ALGAE_3, CoralPositions.E, CoralPositions.F),
    GH(ChoreoPoses.GH, PIEAlgae.FETCH_ALGAE_2, CoralPositions.G, CoralPositions.H),
    IJ(ChoreoPoses.IJ, PIEAlgae.FETCH_ALGAE_3, CoralPositions.I, CoralPositions.J),
    KL(ChoreoPoses.KL, PIEAlgae.FETCH_ALGAE_2, CoralPositions.K, CoralPositions.L);


    public MaybeFlippedPose2d m_pose;
    public PIEAlgae m_algaeHeight;
    public CoralPositions m_coralLeft;
    public CoralPositions m_coralRight;


    AlgaePositions(MaybeFlippedPose2d pose, PIEAlgae algaePosition, CoralPositions coralLeft, CoralPositions coralRight) {
        m_pose = pose;
        m_algaeHeight = algaePosition;
        m_coralLeft = coralLeft;
        m_coralRight = coralRight;
    }
}
