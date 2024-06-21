
package com.gos.crescendo2024.auton.modes;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class OneNoteSourceSideAndLeaveWingChoreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "OneNoteSourceSideAndLeaveWing";

    public OneNoteSourceSideAndLeaveWingChoreo() {
        super(
            NamedCommands.getCommand("AimAndShootIntoSideSpeaker"),
            followChoreoPath(PATH_BASE + ".1")
        );
    }
}

