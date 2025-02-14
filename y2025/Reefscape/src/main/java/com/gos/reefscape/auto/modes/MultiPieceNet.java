package com.gos.reefscape.auto.modes;

import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class MultiPieceNet extends GosAuto {
    public MultiPieceNet(ChassisSubsystem chassis, CombinedCommands combinedCommands, PIE combo, StartingPositions start, List<AlgaePositions> algaePositions) {
        super(start, List.of(CoralPositions.H), algaePositions);
        StringBuilder autoName = new StringBuilder();
        autoName.append(start.toString());
        System.out.print(algaePositions.get(0));

        addCommands(chassis.createResetAndFollowChoreoPathCommand("BlueNetTo" + algaePositions.get(0)));
        addCommands(combinedCommands.fetchAlgae(combo));
        addCommands(chassis.createResetAndFollowChoreoPathCommand(algaePositions.get(0) + "ToBlueNet"));
        addCommands(combinedCommands.scoreAlgaeCommand(combo));
        setName(autoName.toString());


    }
}
