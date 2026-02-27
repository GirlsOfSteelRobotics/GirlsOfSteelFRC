package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import com.gos.rebuilt.commands.CombinedCommand;


import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartLeftPreload extends GosAuto {
    public StartLeftPreload(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.LEFT, List.of(AutoActions.PRELOAD, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartLeftPreload").getPose())));
        addCommands(followChoreoPath("StartLeftPreload"));
        addCommands(combinedCommand.shootBall());


    }
}
