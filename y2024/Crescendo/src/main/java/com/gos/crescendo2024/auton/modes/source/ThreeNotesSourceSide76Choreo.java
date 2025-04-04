
package com.gos.crescendo2024.auton.modes.source;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class ThreeNotesSourceSide76Choreo extends GosAutoMode {

    private static final String PATH_BASE = "ThreeNoteSourceSide76";

    public ThreeNotesSourceSide76Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_SOURCE_SIDE,
            List.of(7, 6),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot preload
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".0"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                    NamedCommands.getCommand("ShooterDefaultRpm")
                ),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".3"),
                    NamedCommands.getCommand("MoveArmToSpeakerAngle"),
                    NamedCommands.getCommand("ShooterDefaultRpm")
                ),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
