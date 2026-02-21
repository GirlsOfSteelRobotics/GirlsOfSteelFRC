package com.gos.rebuilt.enums;

import com.gos.lib.pathing.MaybeFlippedPose2d;

public enum StartingPositions {
    LEFT(null),
    RIGHT(null),
    CENTER(null);


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

