package com.gos.rebuilt.enums;

import com.gos.lib.pathing.MaybeFlippedPose2d;

public enum GainBalls {
    Depot(null),
    Preload(null),
    Outpost(null);


    public MaybeFlippedPose2d m_pose;

    GainBalls(MaybeFlippedPose2d pose) {
        m_pose = pose;
    }

    public String variableName() {
        return switch (this) {
            case Depot -> "Depot";
            case Preload -> "Preload";
            case Outpost -> "Outpost";
            default -> throw new IllegalArgumentException();
        };
    }

}

