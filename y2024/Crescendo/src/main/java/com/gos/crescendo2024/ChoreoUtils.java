package com.gos.crescendo2024;

import com.choreo.lib.Choreo;

public class ChoreoUtils {

    public static MaybeFlippedPose2d getPathStartingPose(String pathName) {
        return new MaybeFlippedPose2d(Choreo.getTrajectory(pathName).getInitialPose());
    }
}
