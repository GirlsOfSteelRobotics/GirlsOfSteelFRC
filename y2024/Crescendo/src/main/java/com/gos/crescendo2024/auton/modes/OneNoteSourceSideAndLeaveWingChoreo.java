
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class OneNoteSourceSideAndLeaveWingChoreo extends GosAutoMode {

    private static final String PATH_BASE = "OneNoteSourceSideAndLeaveWing";

    public OneNoteSourceSideAndLeaveWingChoreo() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
                followChoreoPath(PATH_BASE + ".1")
            )
        );
    }
}

