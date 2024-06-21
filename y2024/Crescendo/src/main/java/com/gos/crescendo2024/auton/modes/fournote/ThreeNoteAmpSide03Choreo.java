
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class ThreeNoteAmpSide03Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "ThreeNoteAmpSide03";

    public ThreeNoteAmpSide03Choreo() {
        super(
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
        );
    }
}

