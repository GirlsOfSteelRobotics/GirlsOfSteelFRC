
package com.gos.crescendo2024.auton.modes.threenote;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class ThreeNotesSourceSide65Choreo extends GosAutoMode {

    private static final String PATH_BASE = "ThreeNoteSourceSide65";

    public ThreeNotesSourceSide65Choreo() {
        super(
            "Three Note Amp Side - 03",
            GosAutoMode.StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
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
                NamedCommands.getCommand("AimAndShootIntoSpeaker"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".3"),
                    NamedCommands.getCommand("IntakePiece")
                ),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".4"),
                    NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                    NamedCommands.getCommand("ShooterDefaultRpm")
                ),
                NamedCommands.getCommand("AimAndShootIntoSpeaker")
            )
        );
    }
}

