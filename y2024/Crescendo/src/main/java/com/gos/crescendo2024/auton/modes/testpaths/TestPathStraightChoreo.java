
package com.gos.crescendo2024.auton.modes.testpaths;

import com.gos.crescendo2024.auton.GosAutoMode;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TestPathStraightChoreo extends GosAutoMode {

    private static final String PATH_BASE = "TestPathStraight";

    public TestPathStraightChoreo() {
        super(
            "Three Note Amp Side - 03",
            StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            followChoreoPath(PATH_BASE)
        );
    }
}

