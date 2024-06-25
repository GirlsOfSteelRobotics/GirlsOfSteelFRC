
package com.gos.crescendo2024.auton.modes.amp;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;
import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteAmpSide045Choreo extends GosAutoMode {

    private static final String PATH_BASE = "FourNoteAmpSide045";

    public FourNoteAmpSide045Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_AMP_SIDE,
            List.of(0, 4, 5),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot Preload
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                combinedCommands.autoAimAndShoot(),

                // Acquire third piece and shoot
                followChoreoPath(PATH_BASE + ".3"),
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".4"),
                followChoreoPath(PATH_BASE + ".5"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
