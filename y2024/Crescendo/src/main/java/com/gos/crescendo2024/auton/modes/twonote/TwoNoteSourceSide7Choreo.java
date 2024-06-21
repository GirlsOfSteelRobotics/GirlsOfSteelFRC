
package com.gos.crescendo2024.auton.modes.twonote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class TwoNoteSourceSide7Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "TwoNotesSourceSide7";

    public TwoNoteSourceSide7Choreo() {
        super(
            NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".1"),
                NamedCommands.getCommand("IntakePiece")
            ),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".2"),
                NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                NamedCommands.getCommand("ShooterDefaultRpm")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker")
        );
    }
}

