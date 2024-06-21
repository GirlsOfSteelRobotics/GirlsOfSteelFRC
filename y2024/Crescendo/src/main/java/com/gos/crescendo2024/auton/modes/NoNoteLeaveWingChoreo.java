
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class NoNoteLeaveWingChoreo extends GosAutoMode {

    private static final String PATH_BASE = "NoNoteLeaveWing";

    public NoNoteLeaveWingChoreo() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                followChoreoPath(PATH_BASE + ".1")
            )
        );
    }
}

