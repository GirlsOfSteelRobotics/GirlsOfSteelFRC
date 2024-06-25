
package com.gos.crescendo2024.auton.modes.source;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TwoNoteSourceSide6Choreo extends GosAutoMode {

    private static final String PATH_BASE = "TwoNoteSourceSide6";

    public TwoNoteSourceSide6Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_SOURCE_SIDE,
            List.of(6),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Drive away from subwoofer and shoot preload
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                combinedCommands.autoAimAndShoot(),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                followChoreoPath(PATH_BASE + ".3"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
