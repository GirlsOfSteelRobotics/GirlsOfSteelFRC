
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class NoNoteLeaveWingChoreo extends GosAutoMode {

    private static final String PATH_BASE = "NoNoteLeaveWing";

    public NoNoteLeaveWingChoreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.CURRENT_LOCATION,
            List.of(),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),
                followChoreoPath(PATH_BASE + ".1")
            )
        );
    }
}
