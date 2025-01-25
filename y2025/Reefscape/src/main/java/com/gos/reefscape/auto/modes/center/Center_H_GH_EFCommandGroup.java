package com.gos.reefscape.auto.modes.center;


import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Center_H_GH_EFCommandGroup extends SequentialCommandGroup {
    public Center_H_GH_EFCommandGroup(GOSSwerveDrive swerveDrive) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("MiddleToH"));
        addCommands(followChoreoPath("HToGHAlgae"));
        addCommands(followChoreoPath("GHAlgaeToProcessor"));
        addCommands(followChoreoPath("ProcessorToEF"));
        addCommands(followChoreoPath("EFToProcessor"));
    }
}
