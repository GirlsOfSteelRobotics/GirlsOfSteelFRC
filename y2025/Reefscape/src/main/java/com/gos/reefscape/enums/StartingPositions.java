package com.gos.reefscape.enums;

import com.gos.reefscape.generated.ChoreoPoses;
import com.gos.reefscape.MaybeFlippedPose2d;

public enum StartingPositions {
    LEFT(ChoreoPoses.STARTING_POS_LEFT),
    RIGHT(ChoreoPoses.STARTING_POS_RIGHT),
    CENTER(ChoreoPoses.STARTING_POS_CENTER);


    public MaybeFlippedPose2d m_pose;

    StartingPositions(MaybeFlippedPose2d pose) {
        m_pose = pose;
    }

    public String variableName() {
        switch (this) {
        case LEFT:
            return "Left";
        case CENTER:
            return "Center";
        case RIGHT:
            return "Right";
        default:
            throw new IllegalArgumentException();
        }
    }

}

