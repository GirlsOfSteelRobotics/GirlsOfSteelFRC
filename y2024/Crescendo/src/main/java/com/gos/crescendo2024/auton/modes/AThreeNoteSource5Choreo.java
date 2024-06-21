
package com.gos.crescendo2024.auton.modes;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class AThreeNoteSource5Choreo extends GosAutoMode {

    private static final String PATH_BASE = "AssholeAuto5";

    public AThreeNoteSource5Choreo() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    Commands.sequence(
                        followChoreoPath(PATH_BASE + ".2")
                    ),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
        );
    }
}

