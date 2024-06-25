
package com.gos.crescendo2024.auton.modes.middle;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class FourNoteMiddle152Choreo extends GosAutoMode {

    private static final String PATH_BASE = "FourNoteMiddle152";

    public FourNoteMiddle152Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_MIDDLE,
            List.of(1, 5, 2),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot preload
                combinedCommands.autoAimAndShoot(),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                followChoreoPath(PATH_BASE + ".3"),
                combinedCommands.autoAimAndShoot(),

                // Acquire third piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".4"),
                followChoreoPath(PATH_BASE + ".5"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
