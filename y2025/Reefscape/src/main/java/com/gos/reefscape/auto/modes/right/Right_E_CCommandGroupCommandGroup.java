package com.gos.reefscape.auto.modes.right;


import com.gos.reefscape.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class Right_E_CCommandGroupCommandGroup extends SequentialCommandGroup {
    public Right_E_CCommandGroupCommandGroup(GOSSwerveDrive swerveDrive, CombinedCommands combinedCommands) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("RightToE"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.levelFour)); //might be dif
        addCommands(followChoreoPath("EToHumanPlayer"));
        addCommands(combinedCommands.fetchPieceFromHPStation());
        addCommands(followChoreoPath("HumanPlayertoC"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.levelFour));//might be dif

    }
}
