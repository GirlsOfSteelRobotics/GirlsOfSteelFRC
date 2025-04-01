package com.gos.reefscape.auto.modes;

import com.gos.reefscape.commands.AutoModeCommandHelpers;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.StartingPositions;

import java.util.List;

public class IceCreamCoral extends GosAuto {
    public IceCreamCoral(AutoModeCommandHelpers autoHelper, CombinedCommands combinedCommands, PIECoral combo, StartingPositions side, List<CoralPositions> coralPositions) {
        super(side, coralPositions, List.of());
        StringBuilder autoName = new StringBuilder(25);
        autoName.append(side.toString()).append(".coral_with_dessert.").append(coralPositions.get(0)).append(combo);
        addCommands(autoHelper.startingPositionToCoralAndScore(side, coralPositions.get(0), combo));
        addCommands(combinedCommands.startLoweringElevatorForAWhile());


        for (int i = 1; i < coralPositions.size(); i++) {
            CoralPositions currentCoral = coralPositions.get(i - 1);
            CoralPositions nextCoral = coralPositions.get(i);
            autoName.append('.').append(nextCoral).append(combo);

            addCommands(autoHelper.driveFromCoralToHpAndFetch(currentCoral, side));
            addCommands(autoHelper.driveFromHpToCoralAndScore(nextCoral, side, combo));

        }

        CoralPositions lastCoral = coralPositions.get(coralPositions.size() - 1);
        addCommands(autoHelper.driveCoralToIceCream(side, lastCoral));
        setName(autoName.toString());


    }

}
