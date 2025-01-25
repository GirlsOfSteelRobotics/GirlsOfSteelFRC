package com.gos.reefscape.auto.modes.left;


import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Left_J_LCommandGroup extends SequentialCommandGroup {
    public Left_J_LCommandGroup(GOSSwerveDrive swerveDrive) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("LeftToJ"));
        addCommands(followChoreoPath("JToLeftHumanPlayer"));
        addCommands(followChoreoPath("LeftHumanPlayerToL"));
    }
}
