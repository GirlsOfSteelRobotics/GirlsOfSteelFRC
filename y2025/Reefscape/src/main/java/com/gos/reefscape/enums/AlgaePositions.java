package com.gos.reefscape.enums;

import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;

public enum AlgaePositions {
    AB(ChoreoPoses.AB, PIE.FETCH_ALGAE_3, CoralPositions.A, CoralPositions.B),
    CD(ChoreoPoses.CD, PIE.FETCH_ALGAE_2, CoralPositions.C, CoralPositions.D),
    EF(ChoreoPoses.EF, PIE.FETCH_ALGAE_3, CoralPositions.E, CoralPositions.F),
    GH(ChoreoPoses.GH, PIE.FETCH_ALGAE_2, CoralPositions.G, CoralPositions.H),
    IJ(ChoreoPoses.IJ, PIE.FETCH_ALGAE_3, CoralPositions.I, CoralPositions.J),
    KL(ChoreoPoses.KL, PIE.FETCH_ALGAE_2, CoralPositions.K, CoralPositions.L);


    public Pose2d m_pose;
    public PIE m_algaePosition;
    public CoralPositions m_coralLeft;
    public CoralPositions m_coralRight;


    AlgaePositions(Pose2d pose, PIE algaePosition, CoralPositions coralLeft, CoralPositions coralRight) {
        m_pose = pose;
        m_algaePosition = algaePosition;
        m_coralLeft = coralLeft;
        m_coralRight = coralRight;
    }
}
