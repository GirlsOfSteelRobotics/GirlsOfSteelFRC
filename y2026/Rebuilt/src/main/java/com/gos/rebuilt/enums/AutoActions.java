package com.gos.rebuilt.enums;

import com.gos.lib.pathing.MaybeFlippedPose2d;

public enum AutoActions {
    Shoot(null),
    ShootClimb(null);


    public MaybeFlippedPose2d m_pose;

    AutoActions(MaybeFlippedPose2d pose) {
        m_pose = pose;
    }

    public String variableName() {
        return switch (this) {
            case Shoot -> "Shoot";
            case ShootClimb -> "Shoot and climb";
            default -> throw new IllegalArgumentException();
        };
    }

}

