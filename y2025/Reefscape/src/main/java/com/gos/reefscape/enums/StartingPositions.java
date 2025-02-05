package com.gos.reefscape.enums;

import com.gos.reefscape.ChoreoPoses;
import edu.wpi.first.math.geometry.Pose2d;

public enum StartingPositions {
    LEFT(ChoreoPoses.STARTING_POS_LEFT),
    RIGHT(ChoreoPoses.STARTING_POS_RIGHT),
    CENTER(ChoreoPoses.STARTING_POS_CENTER);


    public Pose2d m_pose;

    StartingPositions(Pose2d pose) {
        m_pose = pose;

    }

}

