package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.commands.CombinedCommand;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightPreloadOutpost extends GosAuto {
    public StartRightPreloadOutpost(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.RIGHT, List.of(AutoActions.PRELOAD, AutoActions.OUTPOST, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightOutpost").getPose())));
        addCommands(followChoreoPath("StartRightPreloadOutpost.0"));
        addCommands(combinedCommand.shootBall().withTimeout(5));
        addCommands(followChoreoPath("StartRightPreloadOutpost.1"));
        addCommands(chassisSubsystem.createStop());
        addCommands(Commands.waitSeconds(5));
        addCommands(followChoreoPath("StartRightPreloadOutpost.2"));
        addCommands(combinedCommand.shootBall());

    }
}
