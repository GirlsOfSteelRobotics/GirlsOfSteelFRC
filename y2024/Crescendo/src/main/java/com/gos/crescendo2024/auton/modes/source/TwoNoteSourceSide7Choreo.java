
package com.gos.crescendo2024.auton.modes.source;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class TwoNoteSourceSide7Choreo extends GosAutoMode {

    private static final String PATH_BASE = "TwoNotesSourceSide7";

    public TwoNoteSourceSide7Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_SOURCE_SIDE,
            List.of(7),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot preload
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".2"),
                    NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                    NamedCommands.getCommand("ShooterDefaultRpm")
                ),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
