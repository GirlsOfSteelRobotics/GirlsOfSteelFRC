
package com.gos.crescendo2024.auton.modes.testpaths;

import com.gos.crescendo2024.auton.GosAutoMode;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TestPathStraight extends GosAutoMode {

    private static final String PATH_BASE = "StraightLinePath";

    public TestPathStraight() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            followChoreoPath(PATH_BASE)
        );
    }
}

