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

public class LeftTrenchCenter extends GosAuto {
    public LeftTrenchCenter(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, List.of(AutoActions.CROSSTHETRENCH, AutoActions.SHOOOT));

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("LeftTrenchCenter").getPose())));
        addCommands(new ParallelDeadlineGroup(followChoreoPath("LeftTrenchCenter"), combinedCommand.intake()));
        addCommands(chassisSubsystem.createStop());
        addCommands(combinedCommand.shootBall().withTimeout(5));
    }
}


