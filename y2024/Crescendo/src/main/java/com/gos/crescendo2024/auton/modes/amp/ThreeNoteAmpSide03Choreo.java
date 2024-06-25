
package com.gos.crescendo2024.auton.modes.amp;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class ThreeNoteAmpSide03Choreo extends GosAutoMode {

    private static final String PATH_BASE = "ThreeNoteAmpSide03";

    public ThreeNoteAmpSide03Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 3),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Drive away from subwoofer and shoot preload
                Commands.deadline(
                    followChoreoPath(PATH_BASE + ".1"),
                    NamedCommands.getCommand("PrepSpeakerShot")
                ),
                combinedCommands.autoAimAndShoot(),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                NamedCommands.getCommand("Arm down"),
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".3"),
                followChoreoPath(PATH_BASE + ".4"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
