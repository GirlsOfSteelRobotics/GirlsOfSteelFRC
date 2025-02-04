package com.gos.reefscape.auto.modes;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.PIE;
import com.gos.reefscape.commands.CombinedCommands;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class TwoPieceAlgae extends SequentialCommandGroup {
    public TwoPieceAlgae (ChassisSubsystem chassis, CombinedCommands combinedCommands, AlgaePositions firstAlgae, AlgaePositions secondAlgae) {
        addCommands(chassis.createResetAndFollowChoreoPathCommand("StartingPosCenterToH"));
        addCommands(combinedCommands.scoreCoralCommand(PIE.L4));
        addCommands(followChoreoPath("HTo" + firstAlgae));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath(firstAlgae + "ToProcessor"));
        addCommands(combinedCommands.scoreAlgaeCommand(PIE.SCORE_INTO_PROCESSOR));
        addCommands(followChoreoPath("ProcessorTo" + secondAlgae));
        addCommands(combinedCommands.fetchAlgaeTwo());
        addCommands(followChoreoPath(secondAlgae + "ToProcessor"));
        addCommands(combinedCommands.scoreAlgaeCommand(PIE.SCORE_INTO_PROCESSOR));
    }
}
