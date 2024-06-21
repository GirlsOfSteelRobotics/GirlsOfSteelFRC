
package com.gos.crescendo2024.auton.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class DisruptionAuto extends SequentialCommandGroup {

    private static final String PATH_BASE = "DisruptionAuto";

    public DisruptionAuto() {
        super(followChoreoPath(PATH_BASE + ".1"));
    }
}

