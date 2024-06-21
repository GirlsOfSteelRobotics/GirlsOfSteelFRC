
package com.gos.crescendo2024.auton.modes.twonote;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TwoNoteMiddle2Choreo extends GosAutoMode {

    private static final String PATH_BASE = "TwoNoteMiddle2";

    public TwoNoteMiddle2Choreo() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                followChoreoPath(PATH_BASE + ".2"),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
        );
    }
}

