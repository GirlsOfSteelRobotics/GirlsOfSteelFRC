package com.gos.reefscape.auto.modes;


import com.gos.reefscape.commands.AutoModeCommandHelpers;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;

import java.util.List;

@SuppressWarnings("PMD.ClassNamingConventions")
public class MultiPieceCoral extends GosAuto {
    public MultiPieceCoral(AutoModeCommandHelpers autoHelper, CombinedCommands combinedCommands, PIECoral combo, StartingPositions side, List<CoralPositions> coralPositions) {
        super(side, coralPositions, List.of());
        StringBuilder autoName = new StringBuilder();
        autoName.append(side.toString()).append(".multiCoral.").append(coralPositions.get(0)).append(combo);
        addCommands(autoHelper.startingPositionToCoralAndScore(side, coralPositions.get(0), combo));
        addCommands(combinedCommands.startLoweringElevatorForAWhile());


        for (int i = 1; i < coralPositions.size(); i++) {
            CoralPositions currentCoral = coralPositions.get(i - 1);
            CoralPositions nextCoral = coralPositions.get(i);
            autoName.append('.').append(nextCoral).append(combo);

            addCommands(autoHelper.driveFromCoralToHpAndFetch(currentCoral, side));
            addCommands(autoHelper.driveFromHpToCoralAndScore(nextCoral, side, combo));

        }

        addCommands(combinedCommands.goHome());

        setName(autoName.toString());


    }
}
