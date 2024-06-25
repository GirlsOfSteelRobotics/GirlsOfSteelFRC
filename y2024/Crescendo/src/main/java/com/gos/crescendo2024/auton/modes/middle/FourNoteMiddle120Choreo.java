
package com.gos.crescendo2024.auton.modes.middle;

import com.gos.crescendo2024.auton.GosAutoMode;
import com.gos.crescendo2024.commands.CombinedCommands;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.crescendo2024.ChoreoUtils.getPathStartingPose;

public class FourNoteMiddle120Choreo extends GosAutoMode {

    private static final String PATH_BASE = "FourNoteMiddle120";

    public FourNoteMiddle120Choreo(CombinedCommands combinedCommands) {
        super(
            StartPosition.STARTING_LOCATION_MIDDLE,
            List.of(1, 2, 0),

            Commands.sequence(
                combinedCommands.resetPose(getPathStartingPose(PATH_BASE)),

                // Shoot preload
                NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),

                // Acquire first piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".1"),
                combinedCommands.autoAimAndShoot(),

                // Acquire second piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".2"),
                combinedCommands.autoAimAndShoot(),

                // Acquire third piece and shoot
                combinedCommands.followPathWhileIntaking(PATH_BASE + ".3"),
                combinedCommands.autoAimAndShoot()
            )
        );
    }
}
