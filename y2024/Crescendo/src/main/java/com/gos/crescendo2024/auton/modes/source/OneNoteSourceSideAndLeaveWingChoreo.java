
package com.gos.crescendo2024.auton.modes.source;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class OneNoteSourceSideAndLeaveWingChoreo extends GosAutoMode {

    private static final String PATH_BASE = "OneNoteSourceSideAndLeaveWing";

    public OneNoteSourceSideAndLeaveWingChoreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_SOURCE_SIDE,
            List.of(),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
                followChoreoPath(PATH_BASE + ".1")
            )
        );
    }
}
