package com.gos.reefscape.auto.modes;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MultiPieceProcessor extends GosAuto {
    public MultiPieceProcessor(ChassisSubsystem chassis, CombinedCommands combinedCommands, PIECoral combo, CoralPositions coral, StartingPositions start, List<AlgaePositions> algaePositions) {
        super(StartingPositions.CENTER, List.of(CoralPositions.H), algaePositions);
        StringBuilder autoname = new StringBuilder();
        autoname.append(start.toString());

        addCommands(chassis.createResetAndFollowChoreoPathCommand("StartingPos" + start.variableName() + "To" + coral));
        addCommands(combinedCommands.scoreCoralCommand(combo));
        addCommands(followChoreoPath(coral + "To" + algaePositions.get(0)));


        for (int i = 0; i < algaePositions.size() - 1; i++) {
            AlgaePositions currentAlgae = algaePositions.get(i);
            PIEAlgae height = currentAlgae.m_algaeHeight;
            addCommands(combinedCommands.fetchAlgae(height));
            addCommands((followChoreoPath(algaePositions.get(i) + "ToProcessor")));
            addCommands(combinedCommands.scoreAlgaeInProcessorCommand());
            addCommands(followChoreoPath("ProcessorTo" + algaePositions.get(i + 1)));
            autoname.append('.').append(algaePositions.get(i)).append(combo);
        }

        AlgaePositions currentAlgae = algaePositions.get(algaePositions.size() - 1);
        addCommands(combinedCommands.fetchAlgae(currentAlgae.m_algaeHeight));
        addCommands((followChoreoPath(currentAlgae + "ToProcessor")));
        addCommands(combinedCommands.scoreAlgaeInProcessorCommand());

        autoname.append('.').append(currentAlgae).append(combo);
        setName(autoname.toString());
    }
}
