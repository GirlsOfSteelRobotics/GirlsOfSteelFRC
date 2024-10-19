
package com.gos.crescendo2024.auton.modes.source;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class OzoneTwoNoteSource7 extends GosAutoMode {

    private static final String PATH_BASE = "OZONETwoNoteSource7";

    public OzoneTwoNoteSource7(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_SOURCE_SIDE,
            List.of(7),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".0"),
                followChoreoPath(PATH_BASE + ".1"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                followChoreoPath(PATH_BASE + ".3"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
