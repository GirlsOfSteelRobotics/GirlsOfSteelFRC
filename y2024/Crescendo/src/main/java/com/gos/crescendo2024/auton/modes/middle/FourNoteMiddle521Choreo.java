
package com.gos.crescendo2024.auton.modes.middle;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteMiddle521Choreo extends GosAutoMode {

    private static final String PATH_BASE = "FourNoteMiddle521";

    public FourNoteMiddle521Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_MIDDLE,
            List.of(5, 2, 1),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot preload
                combinedCommands.autoAimAndShoot(),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                followChoreoPath(PATH_BASE + ".2"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".3"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".4"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                combinedCommands.autoAimAndShoot(),

                // Acquire third piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".5"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
