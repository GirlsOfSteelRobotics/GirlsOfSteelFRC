package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import org.wpilib.command2.Commands;
import com.gos.rebuilt.commands.CombinedCommand;


import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartMiddleShootMiddle extends GosAuto {
    public StartMiddleShootMiddle(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.CENTER, List.of(AutoActions.PRELOAD, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartMiddleShootMiddle").getPose())));
        addCommands(followChoreoPath("StartMiddleShootMiddle"));
        addCommands(combinedCommand.shootBall());

    }
}
