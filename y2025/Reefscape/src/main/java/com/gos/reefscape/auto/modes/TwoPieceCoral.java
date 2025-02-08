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
    public TwoPieceCoral(ChassisSubsystem swerveDrive, CombinedCommands combinedCommands, PIE combo, StartingPositions side, List<CoralPositions> coralPositions) {
        super (side, coralPositions, List.of());
        String autoName = side.toString();
        addCommands(swerveDrive.createResetAndFollowChoreoPathCommand("StartingPos" + side.variableName() + "To" + coralPositions.get(0)));

        for(int i = 0; i < coralPositions.size() - 1; i++) {
            autoName += "." + coralPositions.get(i) + "" + combo + ".";
            addCommands(combinedCommands.scoreCoralCommand(combo));
            addCommands(followChoreoPath(coralPositions.get(i) + "ToHumanPlayer" + side.variableName()));
            addCommands(combinedCommands.fetchPieceFromHPStation());
            addCommands(followChoreoPath("HumanPlayer" + side.variableName() + "To" + coralPositions.get(i+1)));
        }
        addCommands(combinedCommands.scoreCoralCommand(combo));
        setName(autoName);


    }
}
