package com.gos.reefscape.auto.modes;

import com.gos.reefscape.commands.AutoModeCommandHelpers;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.StartingPositions;

import java.util.List;

public class MultiIceCreamProcessor extends GosAuto {
    public MultiIceCreamProcessor(AutoModeCommandHelpers autoHelper, PIECoral combo, CoralPositions coral, StartingPositions start, AlgaePositions algaePositions) {
        super(start, List.of(coral), List.of(algaePositions));
        StringBuilder autoName = new StringBuilder(30);
        autoName.append(start.toString()).append(".processor.icecream.").append(coral).append(combo);

        // Score the preload
        addCommands(autoHelper.startingPositionToCoralAndScore(start, coral, combo));

        // Drive from the preload score to the first algae
        addCommands(autoHelper.driveCoralToAlgae(coral, algaePositions));

        addCommands(autoHelper.fetchAlgaeThenDriveToProcessorAndScore(algaePositions));

        addCommands(autoHelper.driveProcessorToIceCream(coral, algaePositions));

        addCommands(autoHelper.driveIceCreamToProcessor());

        setName(autoName.toString());
    }
}
