package com.gos.rebuilt.autos;

import com.gos.lib.pathing.ChoreoUtils;
import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Commands;
import com.gos.rebuilt.commands.CombinedCommand;


import java.util.List;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class StartRightOutpost extends GosAuto {
    public StartRightOutpost(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.RIGHT, List.of(AutoActions.OUTPOST, AutoActions.SHOOOT));
        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("StartRightOutpost").getPose())));
        addCommands(followChoreoPath("StartRightOutpost.0"));
        addCommands(Commands.waitSeconds(5));
        addCommands(followChoreoPath("StartRightOutpost.1"));
        addCommands(combinedCommand.shootBall());


    }
}
