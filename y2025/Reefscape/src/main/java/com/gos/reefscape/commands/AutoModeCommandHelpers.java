package com.gos.reefscape.commands;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.PIEAlgae;
import com.gos.reefscape.enums.PIECoral;
import com.gos.reefscape.enums.StartingPositions;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class AutoModeCommandHelpers {
    private final ChassisSubsystem m_chassis;
    private final CombinedCommands m_combinedCommands;

    public AutoModeCommandHelpers(ChassisSubsystem chassis, CombinedCommands combinedCommands) {
        m_chassis = chassis;
        m_combinedCommands = combinedCommands;
    }

    public Command startingPositionToCoralAndScore(StartingPositions start, CoralPositions coral, PIECoral coralHeight) {
        SequentialCommandGroup group = new SequentialCommandGroup();

        group.addCommands(m_chassis.createResetAndFollowChoreoPathCommand("StartingPos" + start.variableName() + "To" + coral));
        group.addCommands(m_combinedCommands.autoScoreCoralCommand(coralHeight));

        return group;
    }

    public Command driveFromCoralToHpAndFetch(CoralPositions coralPosition, StartingPositions hpSide) {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(followChoreoPath(coralPosition + "ToHumanPlayer" + hpSide.variableName())
            .alongWith(m_combinedCommands.goHome()));
        group.addCommands(m_combinedCommands.autoFetchPieceFromHPStation().withTimeout(1.5));
        return group;
    }

    public Command driveFromHpToCoralAndScore(CoralPositions goalCoralPosition, StartingPositions hpSide, PIECoral coralHeight) {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(followChoreoPath("HumanPlayer" + hpSide.variableName() + "To" + goalCoralPosition)
            .alongWith(m_combinedCommands.fetchPieceFromHPStation()));
        // new ConditionalCommand(hasPiece, do nothing, new shimmyCommand)
        group.addCommands(m_combinedCommands.autoScoreCoralCommand(coralHeight));
        return group;
    }

    public Command driveCoralToAlgae(CoralPositions coral, AlgaePositions algae) {
        String pathBase = coral + "To" + algae;

        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(followChoreoPath(pathBase + ".0"));
        group.addCommands(new InstantCommand(() -> m_chassis.driveWithJoystick(0, 0, 0)));
        group.addCommands(m_combinedCommands.autoPieCommand(algae.m_algaeHeight.m_setpoint));
        group.addCommands(followChoreoPath(pathBase + ".1")
            .deadlineFor(m_combinedCommands.fetchAlgae(algae.m_algaeHeight)));

        return group;
    }

    public Command fetchAlgaeThenDriveToProcessorAndScore(AlgaePositions algaePosition) {
        SequentialCommandGroup group = new SequentialCommandGroup();

        group.addCommands(m_combinedCommands.autoFetchAlgae(algaePosition.m_algaeHeight));
        group.addCommands((followChoreoPath(algaePosition + "ToProcessor"))
            .deadlineFor(new WaitCommand(1)
                .andThen(m_combinedCommands.autoPieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint)))

            .deadlineFor(m_combinedCommands.holdAlgaeWhileDriving()));
        group.addCommands(m_combinedCommands.autoScoreAlgaeInProcessorCommand());

        return group;
    }

    public Command driveFromProcessorToAlgae(AlgaePositions algaePosition) {
        return followChoreoPath("ProcessorTo" + algaePosition)
                .deadlineFor(m_combinedCommands.fetchAlgae(algaePosition.m_algaeHeight));
    }

    public Command fetchAlgaeThenDriveToNetAndScore(AlgaePositions algaePosition) {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(m_combinedCommands.autoFetchAlgae(algaePosition.m_algaeHeight));
        group.addCommands((followChoreoPath(algaePosition + "ToBlueNet"))
            .deadlineFor(m_combinedCommands.holdAlgaeWhileDriving()));
        group.addCommands(m_combinedCommands.autoScoreAlgaeInNet());
        return group;
    }

    public Command driveFromNetToAlgae(AlgaePositions algae) {
        return followChoreoPath("BlueNetTo" + algae)
            .deadlineFor(m_combinedCommands.fetchAlgae(algae.m_algaeHeight));
    }

    public Command driveProcessorToIceCream(CoralPositions coral, AlgaePositions algaePositions) {
        return followChoreoPath("ProcessorToRightIceCream")
            .deadlineFor(m_combinedCommands.fetchAlgae(PIEAlgae.ALGAE_LOLLIPOP)).andThen(m_combinedCommands.fetchAlgae(PIEAlgae.ALGAE_LOLLIPOP).withTimeout(1));
    }

    public Command driveIceCreamToProcessor() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands((followChoreoPath("RightIceCreamToProcessor"))
            .deadlineFor(m_combinedCommands.autoPieCommand(PIEAlgae.SCORE_INTO_PROCESSOR.m_setpoint))
            .deadlineFor(m_combinedCommands.holdAlgaeWhileDriving()));
        group.addCommands(m_combinedCommands.autoScoreAlgaeInProcessorCommand());

        return group;
    }

    public Command driveCoralToIceCream(StartingPositions side, CoralPositions lastCoral) {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(followChoreoPath(lastCoral + "To" + side.variableName() + "IceCream")
            .deadlineFor(m_combinedCommands.fetchAlgae(PIEAlgae.ALGAE_LOLLIPOP)).andThen(m_combinedCommands.fetchAlgae(PIEAlgae.ALGAE_LOLLIPOP).withTimeout(1)));
        return group;

    }
}
