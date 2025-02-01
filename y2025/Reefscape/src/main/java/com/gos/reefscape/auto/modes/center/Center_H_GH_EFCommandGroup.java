package com.gos.reefscape.auto.modes.center;


import com.gos.reefscape.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Center_H_GH_EFCommandGroup extends SequentialCommandGroup {


    public Center_H_GH_EFCommandGroup(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("StartingPosCenterToH"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.L4)); //might be dif
        addCommands(followChoreoPath("HToGH"));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath("GHToProcessor"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.SCORE_INTO_PROCESSOR));
        addCommands(followChoreoPath("ProcessorToEF"));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath("EFToProcessor"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.SCORE_INTO_PROCESSOR));

    }
}
