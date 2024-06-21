
package com.gos.crescendo2024.auton.modes.threenote;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class ThreeNoteAmpSide03Choreo extends GosAutoMode {

    private static final String PATH_BASE = "ThreeNoteAmpSide03";

    public ThreeNoteAmpSide03Choreo() {
        super(
            "Three Note Amp Side - 03",
            StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.parallel(
                    followChoreoPath(PATH_BASE + ".2"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                NamedCommands.getCommand("Arm down"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".3"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                followChoreoPath(PATH_BASE + ".4"),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
        );
    }
}

