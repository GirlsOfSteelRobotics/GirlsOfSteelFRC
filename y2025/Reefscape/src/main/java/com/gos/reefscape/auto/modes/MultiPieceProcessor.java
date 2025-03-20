package com.gos.reefscape.auto.modes;

import com.gos.reefscape.commands.AutoModeCommandHelpers;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.StartingPositions;

import java.util.List;

public class MultiPieceProcessor extends GosAuto {
    public MultiPieceProcessor(AutoModeCommandHelpers autoHelper, CombinedCommands combinedCommands, PIECoral combo, CoralPositions coral, StartingPositions start, List<AlgaePositions> algaePositions) {
        super(start, List.of(coral), algaePositions);
        StringBuilder autoName = new StringBuilder();
        autoName.append(start.toString()).append(".processor.").append(coral).append(combo);

        // Score the preload
        addCommands(autoHelper.startingPositionToCoralAndScore(start, coral, combo));

        // Drive from the preload score to the first algae
        addCommands(autoHelper.driveCoralToAlgae(coral, algaePositions.get(0)));

        for (int i = 0; i < algaePositions.size() - 1; i++) {
            // Fetch the algae we are in front of, then drive to the processor and score
            addCommands(autoHelper.fetchAlgaeThenDriveToProcessorAndScore(algaePositions.get(i)));

            // Drive from the processor to the next algae
            addCommands(autoHelper.driveFromProcessorToAlgae(algaePositions.get(i + 1)));

            autoName.append('.').append(algaePositions.get(i));
        }

        AlgaePositions lastAlgae = algaePositions.get(algaePositions.size() - 1);
        addCommands(autoHelper.fetchAlgaeThenDriveToProcessorAndScore(lastAlgae));
        addCommands(combinedCommands.goHome());

        autoName.append('.').append(lastAlgae);
        setName(autoName.toString());
    }
}
