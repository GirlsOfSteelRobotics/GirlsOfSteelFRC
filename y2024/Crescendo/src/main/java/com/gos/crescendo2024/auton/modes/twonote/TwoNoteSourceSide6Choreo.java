
package com.gos.crescendo2024.auton.modes.twonote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TwoNoteSourceSide6Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "TwoNoteSourceSide6";

    public TwoNoteSourceSide6Choreo() {
        super(
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".1"),
                NamedCommands.getCommand("PrepSpeakerShot")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".2"),
                NamedCommands.getCommand("IntakePiece")
            ),
            followChoreoPath(PATH_BASE + ".3"),
            NamedCommands.getCommand("AimAndShootIntoSpeaker")
        );
    }
}

