
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class AThreeNoteSource5Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "AssholeAuto5";

    public AThreeNoteSource5Choreo() {
        super(
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
        );
    }
}

