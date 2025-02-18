package com.gos.reefscape.auto.modes;

import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIEAlgae;

import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MultiPieceNet extends GosAuto {
    public MultiPieceNet(ChassisSubsystem chassis, CombinedCommands combinedCommands, PIECoral combo, CoralPositions coral, StartingPositions start, List<AlgaePositions> algaePositions) {
        super(start, List.of(CoralPositions.H), algaePositions);
        StringBuilder autoName = new StringBuilder();
        autoName.append(start.toString());
        System.out.print(algaePositions.get(0));


        addCommands(chassis.createResetAndFollowChoreoPathCommand("StartingPos" + start.variableName() + "To" + coral));


        //

        for (int i = 0; i < algaePositions.size() - 1; i++) {
            AlgaePositions currentAlgae = algaePositions.get(i);
            PIEAlgae height = currentAlgae.m_algaeHeight;
            addCommands(combinedCommands.fetchAlgae(height));
            addCommands((followChoreoPath(algaePositions.get(i) + "ToBlueNet")));
            addCommands(combinedCommands.scoreAlgaeInNet());
            addCommands(followChoreoPath("BlueNetTo" + algaePositions.get(i + 1)));
            autoName.append('.').append(algaePositions.get(i)).append(combo);
        }

        //
        AlgaePositions currentAlgae = algaePositions.get(algaePositions.size() - 1);
        addCommands(combinedCommands.fetchAlgae(currentAlgae.m_algaeHeight));
        addCommands((followChoreoPath(currentAlgae + "ToBlueNet")));
        addCommands(combinedCommands.scoreAlgaeInNet());

        autoName.append('.').append(currentAlgae).append(combo);
        setName(autoName.toString());


    }
}
