package com.gos.reefscape.auto.modes;


import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class TwoPieceCoral extends GosAuto {
    public TwoPieceCoral(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands, PIE combo, StartingPositions side, CoralPositions firstPiece, CoralPositions secondPiece) {
        super (side, List.of(firstPiece, secondPiece), List.of());
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("StartingPos" + side.variableName() + "To" + firstPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
        addCommands(followChoreoPath(firstPiece + "ToHumanPlayer" + side.variableName()));
        addCommands(combinedCommands.fetchPieceFromHPStation());
        addCommands(followChoreoPath("HumanPlayer" + side.variableName() + "To" + secondPiece));
        addCommands(combinedCommands.scoreCoralCommand(combo));
    }
}
