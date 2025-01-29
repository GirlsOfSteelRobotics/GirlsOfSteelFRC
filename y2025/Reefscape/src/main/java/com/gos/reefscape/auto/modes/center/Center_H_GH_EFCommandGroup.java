package com.gos.reefscape.auto.modes.center;


import com.gos.reefscape.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Center_H_GH_EFCommandGroup extends SequentialCommandGroup {


    public Center_H_GH_EFCommandGroup(GOSSwerveDrive swerveDrive, CombinedCommands combinedCommands) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("MiddleToH"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.L4)); //might be dif
        addCommands(followChoreoPath("HToGHAlgae"));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath("GHAlgaeToProcessor"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.SCORE_INTO_PROCESSOR));
        addCommands(followChoreoPath("ProcessorToEF"));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath("EFToProcessor"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.SCORE_INTO_PROCESSOR));

    }
}
