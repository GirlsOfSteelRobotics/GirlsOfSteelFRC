package com.gos.reefscape.auto.modes;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MultiPieceAlgae extends GosAuto {
    public MultiPieceAlgae(ChassisSubsystem chassis, CombinedCommands combinedCommands, PIE combo, CoralPositions coral, StartingPositions start, List<AlgaePositions> algaePositions) {
        super(StartingPositions.CENTER, List.of(CoralPositions.H), algaePositions);
        StringBuilder autoname = new StringBuilder();
        autoname.append(start.toString());

        addCommands(chassis.createResetAndFollowChoreoPathCommand("StartingPos" + start.variableName() + "To" + coral));
        addCommands(combinedCommands.scoreCoralCommand(combo));
        addCommands(followChoreoPath(coral + "To" + algaePositions.get(0)));


        for (int i = 0; i < algaePositions.size() - 1; i++) {
            addCommands(combinedCommands.fetchAlgae(combo));
            addCommands((followChoreoPath(algaePositions.get(i) + "ToProcessor")));
            addCommands(combinedCommands.scoreAlgaeCommand(combo));
            addCommands(followChoreoPath("ProcessorTo" + algaePositions.get(i + 1)));
            autoname.append('.').append(algaePositions.get(i)).append(combo);
        }
        addCommands(combinedCommands.fetchAlgae(combo));
        addCommands((followChoreoPath(algaePositions.get(algaePositions.size() - 1) + "ToProcessor")));
        addCommands(combinedCommands.scoreAlgaeCommand(combo));

        autoname.append('.').append(algaePositions.get(algaePositions.size() - 1)).append(combo);
        setName(autoname.toString());
    }
}
