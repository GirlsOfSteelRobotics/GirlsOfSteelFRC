package com.gos.reefscape.auto.modes;


import com.gos.reefscape.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.drive.GOSSwerveDrive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class TwoPiece extends SequentialCommandGroup {
    public TwoPiece(GOSSwerveDrive swerveDrive, CombinedCommands combinedCommands, PIE combo, String side, String firstPiece, String secondPiece) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand(side + "To" + firstPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
        addCommands(followChoreoPath(firstPiece + "To" + side + "HumanPlayer"));
        addCommands(combinedCommands.fetchPieceFromHPStation());
        addCommands(followChoreoPath(side + "HumanPlayerTo" + secondPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
    }
}
