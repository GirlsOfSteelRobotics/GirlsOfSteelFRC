package com.gos.rebuilt.enums;

import com.gos.lib.pathing.MaybeFlippedPose2d;
import com.gos.rebuilt.choreo_gen.ChoreoVars;
import com.gos.rebuilt.choreo_gen.ChoreoVars.Poses;

public enum StartingPositions {
    LEFT(new MaybeFlippedPose2d(Poses.leftStartPoint)),
    RIGHT(new MaybeFlippedPose2d(Poses.RightStartPoint)),
    CENTER(new MaybeFlippedPose2d(Poses.centerStartPoint));


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

