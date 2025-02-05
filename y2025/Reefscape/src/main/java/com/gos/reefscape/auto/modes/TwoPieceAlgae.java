package com.gos.reefscape.auto.modes;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class TwoPieceAlgae extends GosAuto {
    public TwoPieceAlgae (ChassisSubsystem chassis, CombinedCommands combinedCommands, CoralPositions coral, StartingPositions start, AlgaePositions firstAlgae, AlgaePositions secondAlgae) {
        super (StartingPositions.CENTER, List.of(CoralPositions.H), List.of(firstAlgae,secondAlgae));
        addCommands(chassis.createResetAndFollowChoreoPathCommand("StartingPos" + start.variableName() + "To" + coral));
        addCommands(combinedCommands.scoreCoralCommand(PIE.L4));
        addCommands(followChoreoPath("HTo" + firstAlgae));
        addCommands(combinedCommands.fetchAlgae(firstAlgae.m_algaePosition));
        addCommands(followChoreoPath(firstAlgae + "ToProcessor"));
        addCommands(combinedCommands.scoreAlgaeCommand(PIE.SCORE_INTO_PROCESSOR));
        addCommands(followChoreoPath("ProcessorTo" + secondAlgae));
        addCommands(combinedCommands.fetchAlgae(secondAlgae.m_algaePosition));
        addCommands(followChoreoPath(secondAlgae + "ToProcessor"));
        addCommands(combinedCommands.scoreAlgaeCommand(PIE.SCORE_INTO_PROCESSOR));
    }
}
