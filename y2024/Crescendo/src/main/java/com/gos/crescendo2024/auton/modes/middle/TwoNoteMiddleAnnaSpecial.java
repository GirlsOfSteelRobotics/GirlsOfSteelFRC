
package com.gos.crescendo2024.auton.modes.middle;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class TwoNoteMiddleAnnaSpecial extends GosAutoMode {

    private static final String PATH_BASE = "TwoNoteMiddleAnnaSpecial";

    public TwoNoteMiddleAnnaSpecial(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_MIDDLE,
            List.of(),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot Preload
                combinedCommands.autoAimAndShoot(),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                followChoreoPath(PATH_BASE + ".2"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
