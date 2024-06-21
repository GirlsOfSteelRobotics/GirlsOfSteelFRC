
package com.gos.crescendo2024.auton.modes.twonote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TwoNoteMiddle1Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "TwoNoteMiddle1";

    public TwoNoteMiddle1Choreo() {
        super(
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".1"),
                NamedCommands.getCommand("IntakePiece")
            ),
            followChoreoPath(PATH_BASE + ".2"),
            NamedCommands.getCommand("AimAndShootIntoSpeaker")
        );
    }
}

