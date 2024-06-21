
package com.gos.crescendo2024.auton.modes.testpaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TestPathStraight extends SequentialCommandGroup {
    public TestPathStraight() {
        super(followChoreoPath("StraightLinePath"));
    }
}

