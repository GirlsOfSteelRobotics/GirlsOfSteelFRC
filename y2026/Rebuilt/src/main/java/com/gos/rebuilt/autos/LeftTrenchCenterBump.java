package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class LeftTrenchCenterBump extends GosAuto {
    public LeftTrenchCenterBump(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, List.of(AutoActions.CROSSTHETRENCH, AutoActions.SHOOOT, AutoActions.CROSSBUMP));

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("LeftTrenchCenterBump").getPose())));
        addCommands(new ParallelDeadlineGroup(followChoreoPath("LeftTrenchCenterBump"), combinedCommand.intake()));
        addCommands(chassisSubsystem.createStop());
        addCommands(combinedCommand.shootBall().withTimeout(5));
    }
}


