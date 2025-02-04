package com.gos.reefscape.auto.modes;


import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class TwoPieceCoral extends SequentialCommandGroup {
    public TwoPieceCoral(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands, PIE combo, StartingPositions side, CoralPositions firstPiece, CoralPositions secondPiece) {
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("StartingPos" + side + "To" + firstPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
        addCommands(followChoreoPath(firstPiece + "ToHumanPlayer" + side));
        addCommands(combinedCommands.fetchPieceFromHPStation());
        addCommands(followChoreoPath("HumanPlayer" + side + "To" + secondPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
    }
}
