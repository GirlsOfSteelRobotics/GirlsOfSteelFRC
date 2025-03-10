package com.gos.reefscape.auto.modes;


import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

@SuppressWarnings("PMD.ClassNamingConventions")
public class MultiPieceCoral extends GosAuto {
    public MultiPieceCoral(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands, PIECoral combo, StartingPositions side, List<CoralPositions> coralPositions) {
        super(side, coralPositions, List.of());
        StringBuilder autoName = new StringBuilder();
        autoName.append(side.toString()).append(".multiCoral");
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("StartingPos" + side.variableName() + "To" + coralPositions.get(0)));

        for (int i = 0; i < coralPositions.size() - 1; i++) {
            autoName.append('.').append(coralPositions.get(i)).append(combo);
            addCommands(combinedCommands.autoScoreCoralCommand(combo));
            addCommands(followChoreoPath(coralPositions.get(i) + "ToHumanPlayer" + side.variableName())
                .alongWith(combinedCommands.goHome()));
            addCommands(combinedCommands.autoFetchPieceFromHPStation());
            addCommands(followChoreoPath("HumanPlayer" + side.variableName() + "To" + coralPositions.get(i + 1)));
        }
        addCommands(combinedCommands.autoScoreCoralCommand(combo));
        autoName.append('.').append(coralPositions.get(coralPositions.size() - 1)).append(combo);
        setName(autoName.toString());


    }
}
