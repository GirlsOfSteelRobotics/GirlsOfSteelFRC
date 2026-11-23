package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import org.wpilib.command2.Commands;
import com.gos.rebuilt.commands.CombinedCommand;


import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightPreload extends GosAuto {
    public StartRightPreload(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.RIGHT, List.of(AutoActions.PRELOAD, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightPreload").getPose())));
        addCommands(followChoreoPath("StartRightPreload"));
        addCommands(combinedCommand.shootBall());

    }
}
